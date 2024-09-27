import pandas as pd
from sklearn.pipeline import Pipeline
from sklearn.compose import ColumnTransformer
from sklearn.preprocessing import StandardScaler, OneHotEncoder, PolynomialFeatures
from sklearn.linear_model import LogisticRegression

class UserPredictor:
    def __init__(self):
        # Define feature lists and transformations for numeric and categorical data
        numeric_features = ['past_purchase_amt', 'total_minutes', 'avg_session_duration', 'days_since_last_visit']
        numeric_transformer = Pipeline(steps=[
            ('scaler', StandardScaler()),  # Standardize numeric features
            ('poly', PolynomialFeatures(degree=2, interaction_only=True, include_bias=False))  # Create polynomial and interaction terms
        ])

        categorical_features = ['badge', 'part_of_day']
        categorical_transformer = OneHotEncoder()  # Convert categorical variables into one-hot vectors

        # Compose preprocessing steps into a single transformer
        self.preprocessor = ColumnTransformer(
            transformers=[
                ('num', numeric_transformer, numeric_features),
                ('cat', categorical_transformer, categorical_features)
            ]
        )

        # Set up the pipeline with preprocessing and logistic regression classifier
        self.model = Pipeline(steps=[
            ('preprocessor', self.preprocessor),
            ('classifier', LogisticRegression(solver='liblinear', penalty='l1', C=1.0))  # L1 penalty for feature selection
        ])

    def _add_log_features(self, users, logs):
        # Predefined categories for 'part_of_day' feature
        categories = ['Night', 'Morning', 'Afternoon', 'Evening', 'Unknown']

        # Convert 'date' to datetime and drop rows with errors
        logs['date'] = pd.to_datetime(logs['date'], errors='coerce')
        logs.dropna(subset=['date'], inplace=True)

        # Calculate aggregated features for users based on logs
        logs['total_minutes'] = logs['duration'] / 60.0
        user_minutes = logs.groupby('id')['total_minutes'].sum().reset_index()
        logs['session_count'] = logs.groupby('id')['date'].transform('nunique')
        logs['avg_session_duration'] = logs['total_minutes'] / logs['session_count']
        avg_session_duration = logs.groupby('id')['avg_session_duration'].mean().reset_index()
        max_date = logs['date'].max()
        logs['days_since_last_visit'] = (max_date - logs.groupby('id')['date'].transform("max")).dt.days
        days_since_last_visit = logs.groupby('id')['days_since_last_visit'].first().reset_index()

        # Handle both cases when 'time' field is available or not
        if 'time' in logs.columns:
            logs['time'] = pd.to_datetime(logs['date']).dt.hour
            logs['part_of_day'] = pd.cut(logs['time'], bins=[0, 6, 12, 18, 24], labels=categories[:-1], right=False, include_lowest=True)
            most_frequent_times = logs.groupby('id')['part_of_day'].agg(lambda x: x.mode()[0] if not x.mode().empty else 'Unknown').reset_index()
        else:
            most_frequent_times = pd.DataFrame(logs['id'].unique(), columns=['id'])
            most_frequent_times['part_of_day'] = 'Unknown'

        # Merge all new features into the users DataFrame and fill missing values
        users = users.merge(user_minutes, on='id', how='left')
        users = users.merge(avg_session_duration, on='id', how='left')
        users = users.merge(days_since_last_visit, on='id', how='left')
        users = users.merge(most_frequent_times, on='id', how='left')
        users['part_of_day'] = users['part_of_day'].astype('category').cat.set_categories(categories)
        users.fillna({'total_minutes': 0, 'avg_session_duration': 0, 'days_since_last_visit': 0, 'part_of_day': 'Unknown'}, inplace=True)

        return users

    def fit(self, train_users, train_logs, train_y):
        # Fit the model using training data
        train_users_with_logs = self._add_log_features(train_users, train_logs)
        train_x = train_users_with_logs[['past_purchase_amt', 'total_minutes', 'avg_session_duration', 'days_since_last_visit', 'badge', 'part_of_day']]
        train_y = train_y['clicked']
        self.model.fit(train_x, train_y)

    def predict(self, test_users, test_logs):
        # Predict using the model on new data
        test_users_with_logs = self._add_log_features(test_users, test_logs)
        test_x = test_users_with_logs[['past_purchase_amt', 'total_minutes', 'avg_session_duration', 'days_since_last_visit', 'badge', 'part_of_day']]
        predictions = self.model.predict(test_x)
        return predictions
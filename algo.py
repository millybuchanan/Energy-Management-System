# Import necessary libraries
import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression

# Load original data into a pandas dataframe
df = pd.read_csv('energy_consumption.csv')

# Split data into training and testing sets
train_data = df.iloc[:int(len(df)*0.8)]
test_data = df.iloc[int(len(df)*0.8):]

# Define feature and target variables
train_features = train_data[['temperature', 'humidity', 'occupancy', 'fan_speed', 'light_intensity']].values
train_target = train_data['energy_consumption'].values

# Create a linear regression model and train it on the training data
lr_model = LinearRegression()
lr_model.fit(train_features, train_target)

# Use the trained model to make predictions on the test data
test_features = test_data[['temperature', 'humidity', 'occupancy', 'fan_speed', 'light_intensity']].values
test_predictions = lr_model.predict(test_features)

# Evaluate the performance of the model on the test data using mean squared error
mse = np.mean((test_predictions - test_data['energy_consumption'].values)**2)

# Print the mean squared error
print('Mean Squared Error: ', mse)

# Load new data into a pandas dataframe
new_data = pd.read_csv('new_energy_consumption.csv')

# Add new data to the original training dataset
updated_train_data = pd.concat([train_data, new_data])

# Define feature and target variables for the updated training data
updated_train_features = updated_train_data[['temperature', 'humidity', 'occupancy', 'fan_speed', 'light_intensity']].values
updated_train_target = updated_train_data['energy_consumption'].values

# Retrain the model on the updated training data
lr_model.fit(updated_train_features, updated_train_target)

# Use the trained model to make predictions on the test data
updated_test_predictions = lr_model.predict(test_features)

# Evaluate the performance of the updated model on the test data using mean squared error
updated_mse = np.mean((updated_test_predictions - test_data['energy_consumption'].values)**2)

# Print the updated mean squared error
print('Updated Mean Squared Error: ', updated_mse)
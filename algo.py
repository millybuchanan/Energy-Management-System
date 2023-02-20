# Import necessary libraries
import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression

# Load data into a pandas dataframe
df = pd.read_csv('energy_consumption.csv')

# Split data into training and testing sets
train_data = df.iloc[:int(len(df)*0.8)]
test_data = df.iloc[int(len(df)*0.8):]

# Define feature and target variables
train_features = train_data[['temperature', 'humidity', 'occupancy', 'fan_speed', 'light_intensity']]

# Create a new dataframe to store the one-hot encoded schedule
train_schedule = pd.DataFrame(index=train_data.index, columns=['day_0', 'day_1', 'day_2', 'day_3', 'day_4', 'day_5', 'day_6',
                                                              'hour_0', 'hour_1', 'hour_2', 'hour_3', 'hour_4', 'hour_5', 'hour_6',
                                                              'hour_7', 'hour_8', 'hour_9', 'hour_10', 'hour_11', 'hour_12', 'hour_13',
                                                              'hour_14', 'hour_15', 'hour_16', 'hour_17', 'hour_18', 'hour_19', 'hour_20',
                                                              'hour_21', 'hour_22', 'hour_23'])

# Fill in the one-hot encoded schedule using the 'schedule' column in the training data
for i, row in train_data.iterrows():
    day_index = row['schedule_day']
    hour_index = row['schedule_hour']
    train_schedule.loc[i, f'day_{day_index}'] = 1
    train_schedule.loc[i, f'hour_{hour_index}'] = 1

# Concatenate the one-hot encoded schedule with the other predictor variables
train_features = pd.concat([train_features, train_schedule], axis=1)

train_target = train_data['energy_consumption'].values

# Create a linear regression model and train it on the training data
lr_model = LinearRegression()
lr_model.fit(train_features, train_target)

# Create the one-hot encoded schedule for the test data
test_schedule = pd.DataFrame(index=test_data.index, columns=['day_0', 'day_1', 'day_2', 'day_3', 'day_4', 'day_5', 'day_6',
                                                             'hour_0', 'hour_1', 'hour_2', 'hour_3', 'hour_4', 'hour_5', 'hour_6',
                                                             'hour_7', 'hour_8', 'hour_9', 'hour_10', 'hour_11', 'hour_12', 'hour_13',
                                                             'hour_14', 'hour_15', 'hour_16', 'hour_17', 'hour_18', 'hour_19', 'hour_20',
                                                             'hour_21', 'hour_22', 'hour_23'])
for i, row in test_data.iterrows():
    day_index = row['schedule_day']
    hour_index = row['schedule_hour']
    test_schedule.loc[i, f'day_{day_index}'] = 1
    test_schedule.loc[i, f'hour_{hour_index}'] = 1

# Concatenate the one-hot encoded schedule with the other predictor variables
test_features = pd.concat([test_data[['temperature', 'humidity', 'occupancy', 'fan_speed', 'light_intensity']], test_schedule], axis=1)

test_predictions = lr_model.predict(test_features)

mse = np.mean((test_predictions - test_data['energy_consumption'].values)**2)

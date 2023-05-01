/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.example.compose.jetsurvey.theme.JetsurveyTheme
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.example.compose.jetsurvey.survey.JetsurveyNavHost

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
            Amplify.DataStore.start(
                { Log.i("Amplify", "DataStore started bruh") },
                { Log.e("Amplify", "Error starting DataStore", it) }
            )
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
        setContent {
            JetsurveyTheme {
                JetsurveyNavHost()
            }
        }
    }
}


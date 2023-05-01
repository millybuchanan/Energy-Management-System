/*
 * Copyright 2022 The Android Open Source Project
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

@file:Suppress("DEPRECATION")

package com.example.compose.jetsurvey.screens

import android.os.AsyncTask
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.question.SliderQuestion
import com.example.compose.jetsurvey.theme.JetsurveyTheme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@Composable
@Preview
fun FeedbackQuestionPreview() {
    JetsurveyTheme() {
        //val DashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory())
        var temp : Float = 0.4f;
        var humidity : Float = 0.4f;
        var light : Float = 0.4f;

        val url = "http://172.20.10.5:5000/button"
        var requestBody = "";


        Column {
            Row  {
                Text(
                    text = stringResource(R.string.Feeling),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.8f)

                )
            }
            Row  {
                    SliderQuestion(
                        titleResourceId = R.string.FeedbackTemp,
                        value = 0.4f,
                        steps = 5,
                        onValueChange = {
                            temp = it;
                            Log.i("SurveyNavigation", "temp change: $temp")
                        },
                        startTextResource = R.string.TooCold,
                        neutralTextResource = R.string.Satisfactory,
                        endTextResource = R.string.TooHot,
                    )
                }

            Row  {
                SliderQuestion(
                    titleResourceId = R.string.FeedbackHumidity,
                    value = 0.4f,
                    steps = 5,
                    onValueChange = {
                        humidity = it;
                        Log.i("SurveyNavigation", "humidity change: $humidity")
                    },
                    startTextResource = R.string.TooDry,
                    neutralTextResource = R.string.Satisfactory,
                    endTextResource = R.string.TooHumid,
                )
            }
            Row  {
                SliderQuestion(
                    titleResourceId = R.string.FeedbackLight,
                    value = 0.4f,
                    steps = 5,
                    onValueChange = {
                        light = it;
                        Log.i("SurveyNavigation", "light change: $light")
                    },
                    startTextResource = R.string.TooDark,
                    neutralTextResource = R.string.Satisfactory,
                    endTextResource = R.string.TooBright,
                )


            }
            //Spacer(modifier = Modifier.height(45.dp))
            Button(
                onClick = {
                    val task = FeedbackSubmitTask()
                    task.execute(temp, humidity, light)
                    //requestBody = "{\"temp:\": \"${temp}\" \"humidity:\": \"${humidity}\" \"brightness:\": \"${light}\"}"
                    //postData(url, requestBody)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(text = stringResource(id = R.string.SubmitFeedback))
            }
        }
    }
}

private fun postData(url: String, requestBody: String) {
    try {
        val obj = URL(url)
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection

        // Set the request method to POST
        con.setRequestMethod("POST")

        // Set headers
        con.setRequestProperty("Content-Type", "application/json")

        // Enable output and input streams
        con.setDoOutput(true)
        con.setDoInput(true)

        // Write the request body to the output stream
        val wr = OutputStreamWriter(con.getOutputStream())
        wr.write(requestBody)
        wr.flush()
        wr.close()

        // Get the response code
        val responseCode: Int = con.getResponseCode()

        // Read the response body
        val `in` = BufferedReader(InputStreamReader(con.getInputStream()))
        var inputLine: String?
        val response = StringBuilder()
        while (`in`.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        `in`.close()

        // Print the response
        Log.d("POST Response", response.toString())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Suppress("DEPRECATION")
private open class FeedbackSubmitTask : AsyncTask<Float?, Int?, Long?>() {
    protected override fun doInBackground(vararg urls: Float?): Long {
        var totalSize: Long = 0
        val url = "http://100.70.8.62:5000/button"
        var requestBody = "";
        requestBody = "{\"temp:\": \"${urls[0]}\", \"humidity:\": \"${urls[1]}\", \"brightness:\": \"${urls[2]}\"}"
        postData(url, requestBody)

        return totalSize
    }

    override fun onProgressUpdate(vararg progress: Int?) {
        //setProgressPercent(progress[0])
    }

}

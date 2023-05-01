package com.example.compose.jetsurvey.screens

import android.os.AsyncTask
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
fun PreferencesScreen() {

    var temp : Float = 0.4f;
    var humidity : Float = 0.4f;
    var light : Float = 0.4f;

    val url = "http://172.20.10.5:5000/sensorChange"
    var requestBody = "";

    Column {
        Text(
            text = "Preferences",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        JetsurveyTheme() {
            Surface {
                SliderQuestion(
                    titleResourceId = R.string.preference_energy_consumption,
                    value = 0.4f,
                    steps = 5,
                    onValueChange = {},
                    startTextResource = R.string.low_consumption,
                    neutralTextResource = R.string.mid_consumption,
                    endTextResource = R.string.high_consumption
                )
            }
        }

        JetsurveyTheme() {
            Surface {
                SliderQuestion(
                    titleResourceId = R.string.preference_brightness,
                    value = 0.4f,
                    steps = 5,
                    onValueChange = {
                        light = it;
                    },
                    startTextResource = R.string.low_brightness,
                    neutralTextResource = R.string.mid_brightness,
                    endTextResource = R.string.high_brightness
                )
            }
        }

        JetsurveyTheme() {
            Surface {
                SliderQuestion(
                    titleResourceId = R.string.preference_temperature,
                    value = 0.4f,
                    steps = 5,
                    onValueChange = {
                        temp = it;
                    },
                    startTextResource = R.string.low_temperature,
                    neutralTextResource = R.string.mid_temperature,
                    endTextResource = R.string.high_temperature
                )
            }
            Button(
                onClick = {
                    //val task = EnvironmentChangeTask()
                    //task.execute(temp, light)
                    //requestBody = "{\"setTemp:\": \"${temp}\" \"setBrightness:\": \"${light}\"}"
                    //postData(url, requestBody)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(text = stringResource(id = R.string.SubmitFeedback))
            }
        }

        // Add more preference options here
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
private open class EnvironmentChangeTask : AsyncTask<Float?, Int?, Long?>() {
    override fun doInBackground(vararg urls: Float?): Long {
        var totalSize: Long = 0
        val url = "http://100.70.2.163:5000/sensorChange"
        var requestBody = "";
        requestBody = "{\"temp:\": \"${urls[0]}\", \"brightness:\": \"${urls[1]}\"}"
        postData(url, requestBody)

        return totalSize
    }

    override fun onProgressUpdate(vararg progress: Int?) {
        //setProgressPercent(progress[0])
    }

}


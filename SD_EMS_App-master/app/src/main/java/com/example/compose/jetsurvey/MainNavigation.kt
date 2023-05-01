@file:Suppress("DEPRECATION")

package com.example.compose.jetsurvey

import android.os.AsyncTask
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.jetsurvey.screens.ProfilePagePreview
import com.example.compose.jetsurvey.screens.DashboardScreen
import com.example.compose.jetsurvey.screens.FeedbackQuestionPreview
import com.example.compose.jetsurvey.screens.PreferencesScreen
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

var currentBrightness = 0;
var currentTemp = 0.0;
var currentHumidity = 0.0;



object Menu_Destinations {

    const val DASHBOARD_ROUTE = "dashboard"
    const val PREFERENCES_ROUTE = "preferences"
    const val FEEDBACK_ROUTE = "feedback"
    const val PROFILE_ROUTE = "profile"
}

@Composable
fun LoginApplication(
    navController: NavHostController = rememberNavController(),

) {
    val t = Thread(Runnable {
        while (true) {
            // TO DO:
            // Update all sensor info with this call (global variables)
            Thread.sleep(30000)
            val task = SensorTask()
            task.execute()
        }
    })
    t.start()
    val t2 = Thread(Runnable {
        while (true) {
            // TO DO:
            // Update all sensor info with this call (global variables)
            val task = RestTask()
            task.execute()
            Thread.sleep(180000)
        }
    })
    t2.start()
    NavHost(
        navController = navController,
        startDestination = Menu_Destinations.DASHBOARD_ROUTE,
    ) {
        var i = 0;
        composable(Menu_Destinations.DASHBOARD_ROUTE) {
            // TO DO:
            // Make GET request in this file, so that
            // values are updated every time the user
            // navigates to the dashboard screen
            val task = RestTask()
            task.execute()

            DashboardScreen(name = "Energy Saver", (currentTemp.toString() + " F"), currentBrightness.toString() + " %", currentHumidity.toString() + " %")
        }

        composable(Menu_Destinations.PREFERENCES_ROUTE) {
            PreferencesScreen()
        }

        composable(Menu_Destinations.FEEDBACK_ROUTE) {
            FeedbackQuestionPreview()
        }

        composable(Menu_Destinations.PROFILE_ROUTE) {
            ProfilePagePreview()
        }

    }
}

 fun postData(url: String, requestBody: String) {
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
private open class SensorTask : AsyncTask<Float?, Int?, Long?>() {
    protected override fun doInBackground(vararg urls: Float?): Long {
        //val count = urls.size
        var totalSize: Long = 0
        //val url = "http://100.70.2.163:5000/loop"
        val url = "http://100.70.8.62:5000/loop"
        var requestBody = "";
        requestBody = "{\"Thread work on:\"}"
        postData(url, requestBody)


        return totalSize
    }

    protected override fun onProgressUpdate(vararg progress: Int?) {
        //setProgressPercent(progress[0])
    }

    fun onPostExecute(temp: Float, humidity: Float, light: Float) {
        Log.i("HTTP Request","HTTP Request Success")
    }
}

@Suppress("DEPRECATION")
private open class RestTask : AsyncTask<String?, Void?, String?>() {

    // private open class SignInSubmitTask : AsyncTask<String?, Int?, Long?>() {
    protected override fun doInBackground(vararg params: String?): String {
        //protected override fun doInBackground(vararg urls: String?): Long {
        //val url = "http://100.70.2.163:5000/button"
        val url = "http://100.70.8.62:5000/button"
        var result: String = ""
        try {
            // Create an HttpURLConnection object
            val apiUrl = URL(url)
            val connection: HttpURLConnection = apiUrl.openConnection() as HttpURLConnection

            // Set request method to GET
            connection.setRequestMethod("GET")

            // Connect to the server
            connection.connect()

            // Read the response from the server
            val inputStream: InputStream = connection.getInputStream()
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            result = stringBuilder.toString()

            val jsonObject = JSONObject(result)

            currentTemp = jsonObject.get("temp") as Double
            currentBrightness = jsonObject.get("light_pwm") as Int
            currentHumidity = jsonObject.get("humidity") as Double

        } catch (e: IOException) {
            Log.e(TAG, "Error while making REST request", e)
        }
        Log.d(TAG, "REST response: " + result);
        return result
    }

    @Override
    protected fun onPostExecute(result: String)
    {
        // Handle the result of the network request
        if (result != null) {
            Log.d(TAG, "REST response: " + result);
        } else {
            Log.e(TAG, "Error while making REST request");
        }
    }

    companion object {
        private const val TAG = "RestTask"
    }
}
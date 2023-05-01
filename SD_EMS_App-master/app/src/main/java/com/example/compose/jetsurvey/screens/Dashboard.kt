@file:Suppress("DEPRECATION")

package com.example.compose.jetsurvey.screens

import android.os.AsyncTask
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun DashboardScreen(name: String, tempValue: String, lightValue: String, humidValue: String) {
    val itemsArray = listOf(
        DashboardItem("Temperature", Icons.Filled.Thermostat, tempValue),
        DashboardItem("Lights", Icons.Filled.Lightbulb, lightValue),
        DashboardItem("Humidity", Icons.Filled.Water, humidValue),
        DashboardItem("Energy Usage", Icons.Filled.ElectricalServices, "")
    )

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hi $name!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val gridSize = 2

        itemsArray.chunked(gridSize).forEach { rowItems ->
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                    rowItems.forEach {
                            itemsArray -> (
                            DashboardItemCard(itemsArray)
                            )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Energy Saving Mode",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            val checked = remember { mutableStateOf(true) }
            Switch(
                checked = checked.value,
                onCheckedChange = { checked.value = it },
            )
        }
        Row {
            Button(
                onClick = {
                    // Sensor data is pulled here via GET request
                    // TO DO:
                    // 1) Navigate to energy saving website on click
                    //    only if I'm unable to navigate to the site
                    //    by pressing the energy saving icon
                    uriHandler.openUri("https://web.emporiaenergy.com/#/home")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = true
            ) {
                Text(text = stringResource(id = R.string.EnergyUsageInfo))
            }
        }
    }
}

data class DashboardItem(var title: String, var icon: ImageVector, var info: String)

@Composable
fun DashboardItemCard(item: DashboardItem) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .size(150.dp)
            .clickable {
                false
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(30.dp)

            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = item.info,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Suppress("DEPRECATION")
private open class RestTask : AsyncTask<String?, Void?, String?>() {

    override fun doInBackground(vararg params: String?): String {
        val url = "http://100.70.2.163:5000/button"
        var result = ""
        try {
            // Create an HttpURLConnection object
            val apiUrl = URL(url)
            val connection: HttpURLConnection = apiUrl.openConnection() as HttpURLConnection

            // Set request method to GET
            connection.requestMethod = "GET"

            // Connect to the server
            connection.connect()

            // Read the response from the server
            val inputStream: InputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            result = stringBuilder.toString()
        } catch (e: IOException) {
            Log.e(TAG, "Error while making REST request", e)
        }
        Log.d(TAG, "REST response: $result")
        return result
    }

    companion object {
        private const val TAG = "RestTask"
    }
}






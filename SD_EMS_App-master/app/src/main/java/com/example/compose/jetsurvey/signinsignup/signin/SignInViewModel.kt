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

package com.example.compose.jetsurvey.signinsignup.signin

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.compose.jetsurvey.signinsignup.UserRepository
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    /**
     * Consider all sign ins successful
     */
    suspend fun signIn(email: String, password: String): Boolean {
        val task = SignInSubmitTask()
        task.execute(email)
        return UserRepository.signInSuspend(email, password)
    }

    fun signInWithCallback(email: String, password: String, onSignInResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = signIn(email, password)
            onSignInResult(success)
        }
    }

    fun signInAsGuest(onSignInAsGuest: () -> Unit) {
        UserRepository.signInAsGuest()
        onSignInAsGuest()
    }
}

class SignInViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
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
        con.getResponseCode()

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
private open class SignInSubmitTask : AsyncTask<String?, Int?, Long?>() {
    protected override fun doInBackground(vararg urls: String?): Long {
        var totalSize: Long = 0
        val url = "http://100.70.8.62:5000/login"
        var requestBody = "";
        requestBody = "{\"SIGN IN E-MAIL:\": \"${urls[0]}\"}"
        postData(url, requestBody)

        return totalSize
    }

    override fun onProgressUpdate(vararg progress: Int?) {
        //setProgressPercent(progress[0])
    }

}

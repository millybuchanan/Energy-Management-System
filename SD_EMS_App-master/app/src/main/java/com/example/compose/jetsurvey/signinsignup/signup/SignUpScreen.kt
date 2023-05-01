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

@file:Suppress("DEPRECATION")

package com.example.compose.jetsurvey.signinsignup.signup

import android.os.AsyncTask
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.signinsignup.*
import com.example.compose.jetsurvey.signinsignup.validation.ConfirmPasswordState
import com.example.compose.jetsurvey.signinsignup.validation.EmailState
import com.example.compose.jetsurvey.signinsignup.validation.PasswordState
import com.example.compose.jetsurvey.theme.JetsurveyTheme
import com.example.compose.jetsurvey.theme.stronglyDeemphasizedAlpha
import com.example.compose.jetsurvey.util.supportWideScreen
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class) // Scaffold is experimental in m3
@Composable
fun SignUpScreen(
    email: String?,
    onSignUpSubmitted: (email: String, password: String) -> Unit,
    onSignInAsGuest: () -> Unit,
    onNavUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = stringResource(id = R.string.create_account),
                onNavUp = onNavUp,
            )
        },
        content = { contentPadding ->
            SignInSignUpScreen(
                onSignInAsGuest = onSignInAsGuest,
                contentPadding = contentPadding,
                modifier = Modifier.supportWideScreen()
            ) {
                Column {
                    SignUpContent(
                        email = email,
                        onSignUpSubmitted = onSignUpSubmitted
                    )
                }
            }
        }
    )
}

@Composable
fun SignUpContent(
    email: String?,
    onSignUpSubmitted: (email: String, password: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }
        val emailState = remember { EmailState(email) }
        Email(emailState, onImeAction = { passwordFocusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
            modifier = Modifier.focusRequester(passwordFocusRequest)
        )

        Spacer(modifier = Modifier.height(16.dp))
        val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
        Password(
            label = stringResource(id = R.string.confirm_password),
            passwordState = confirmPasswordState,
            onImeAction = { onSignUpSubmitted(emailState.text, passwordState.text) },
            modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.terms_and_conditions),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSignUpSubmitted(emailState.text, passwordState.text)
                //val task = CreateAccountTask()
                //task.execute(email)
                      },
            modifier = Modifier.fillMaxWidth(),
            enabled = emailState.isValid &&
                passwordState.isValid && confirmPasswordState.isValid
        ) {
            Text(text = stringResource(id = R.string.create_account))
        }
    }
}

@Preview(widthDp = 1024)
@Composable
fun SignUpPreview() {
    JetsurveyTheme {
        SignUpScreen(
            email = null,
            onSignUpSubmitted = { _, _ -> },
            onSignInAsGuest = {},
            onNavUp = {},
        )
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
private open class CreateAccountTask : AsyncTask<String?, Int?, Long?>() {
    @Deprecated("Deprecated in Java")
    protected override fun doInBackground(vararg urls: String?): Long {
        //val count = urls.size
        var totalSize: Long = 0
        val url = "http://100.70.2.163:5000/button"
        var requestBody = "";
        requestBody = "{\"CREATE ACCOUNT E-MAIL:\": \"${urls[0]}\"}"
        postData(url, requestBody)


        return totalSize
    }

    @Deprecated("Deprecated in Java")
    protected override fun onProgressUpdate(vararg progress: Int?) {
        //setProgressPercent(progress[0])
    }

    fun onPostExecute(temp: Float, humidity: Float, light: Float) {
        Log.i("HTTP Request","HTTP Request Success")
    }
}

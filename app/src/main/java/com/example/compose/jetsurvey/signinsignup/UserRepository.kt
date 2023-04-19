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

package com.example.compose.jetsurvey.signinsignup

import android.util.Log
import androidx.compose.runtime.Immutable
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.TestModel
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine



sealed class User {
    @Immutable
    data class LoggedInUser(val email: String) : User()
    object GuestUser : User()
    object NoUserLoggedIn : User()
}

/**
 * Repository that holds the logged in user.
 *
 * In a production app, this class would also handle the communication with the backend for
 * sign in and sign up.
 */
object UserRepository {

    private var _user: User = User.NoUserLoggedIn
    val user: User
        get() = _user

    suspend fun signIn(email: String, password: String): Boolean {
        return signInSuspend(email, password)
    }

    suspend fun signInSuspend(email: String, password: String): Boolean = suspendCancellableCoroutine { continuation ->
        val predicate = TestModel.USERNAME.eq(email).and(TestModel.PASSWORD.eq(password))

        try {
            Amplify.DataStore.query(
                TestModel::class.java,
                predicate,
                { items ->
                    val signedIn = if (items.hasNext()) {
                        _user = User.LoggedInUser(email)
                        Log.i("UserRepository", "signIn successful")
                        true
                    } else {
                        Log.i("UserRepository", "signIn failed")
                        false
                    }
                    continuation.resume(signedIn)
                },
                { failure ->
                    Log.e("Tutorial", "Could not query DataStore", failure)
                    continuation.resume(false)
                }
            )
        } catch (exception: Exception) {
            Log.e("UserRepository", "Error querying item from DataStore", exception)
            continuation.resume(false)
        }
    }




    @Suppress("UNUSED_PARAMETER")
    fun signUp(email: String, password: String, onSignUpComplete: (success: Boolean) -> Unit) {
        Log.i("UserRepository", "signUp called")

        CoroutineScope(Dispatchers.IO).launch {
            // Check if the user already exists
            val userExists = isKnownUserEmail(email)
            if (userExists) {
                Log.i("UserRepository", "User already exists")
                withContext(Dispatchers.Main) {
                    onSignUpComplete(false)
                }
                return@launch
            }

            _user = User.LoggedInUser(email)
            val item = TestModel.Builder()
                .username(email)
                .password(password)
                .build()

            try {
                val success = saveToDataStore(item)
                withContext(Dispatchers.Main) {
                    onSignUpComplete(success)
                }
            } catch (exception: Exception) {
                Log.e("UserRepository", "Error saving item to DataStore", exception)
                withContext(Dispatchers.Main) {
                    onSignUpComplete(false)
                }
            }
        }
    }


    private suspend fun saveToDataStore(item: TestModel): Boolean {
        return suspendCoroutine { continuation ->
            Amplify.DataStore.save(
                item,
                { success ->
                    Log.i("Amplify", "Saved item: " + success.item().username)
                    continuation.resume(true)
                },
                { error ->
                    Log.e("Amplify", "Could not save item to DataStore", error)
                    continuation.resumeWith(Result.failure(error))
                }
            )
        }
    }


    fun signInAsGuest() {
        _user = User.GuestUser
    }

    suspend fun isKnownUserEmail(email: String): Boolean = suspendCancellableCoroutine { continuation ->
        // if the email contains "sign up" we consider it unknown
        val predicate = TestModel.USERNAME.eq(email)

        try {
            Amplify.DataStore.query(
                TestModel::class.java,
                predicate,
                { items ->
                    var flag = false
                    while (items.hasNext()) {
                        val item = items.next()
                        if (item.username == email) {
                            flag = true
                        }
                        Log.i("Amplify", "Queried item: " + item.username)
                    }
                    Log.i("Amplify", "Checking DB: ")
                    continuation.resume(flag)
                },
                { failure ->
                    Log.e("Tutorial", "Could not query DataStore", failure)
                    continuation.resume(false)
                }
            )
        } catch (exception: Exception) {
            Log.e("UserRepository", "Error querying item from DataStore", exception)
            continuation.resume(false)
        }
    }
}

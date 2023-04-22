/*
 * Copyright 2023 The Android Open Source Project
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
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInRoute(
    email: String?,
    onSignInSubmitted: (String, String) -> Unit,
    onSignInAsGuest: () -> Unit,
    onNavUp: () -> Unit,
) {
    Log.i("SignInRoute", "signIn called inside signInRoute function")
    val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory())
    SignInScreen(
        email = email,
        onSignInSubmitted = { email, password ->
            Log.i("SignInRoute", "onSignInSubmitted called inside signInRoute function")
            signInViewModel.signInWithCallback(email, password) { success ->
                if (success) {
                    Log.i("SignInRoute", "signInViewModel.signIn(email, password) called inside signInRoute function")
                    onSignInSubmitted(email, password)
                } else {
                    Log.i("SignInRoute", "failed")
                }
            }
        },
        onSignInAsGuest = {
            Log.i("SignInRoute", "signInAsGuest called inside signInRoute function")
            signInViewModel.signInAsGuest(onSignInAsGuest)
        },
        onNavUp = onNavUp,
    )
}







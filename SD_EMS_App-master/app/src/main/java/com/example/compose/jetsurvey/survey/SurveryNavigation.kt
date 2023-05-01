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

package com.example.compose.jetsurvey.survey

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.jetsurvey.MainScreenView
import com.example.compose.jetsurvey.signinsignup.*
import com.example.compose.jetsurvey.signinsignup.signin.SignInRoute
import com.example.compose.jetsurvey.signinsignup.signup.SignUpRoute
import kotlinx.coroutines.launch

var username: String? = ""
object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SIGN_UP_ROUTE = "signup/{email}"
    const val SIGN_IN_ROUTE = "signin/{email}"
    const val SURVEY_ROUTE = "survey"
    const val SURVEY_RESULTS_ROUTE = "surveyresults"
    const val MAIN_SCREEN_ROUTE = "mainscreen"
}

@Composable
fun JetsurveyNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.WELCOME_ROUTE,
    ) {
        composable(Destinations.WELCOME_ROUTE) {
            WelcomeRoute(
                onNavigateToSignIn = {
                    navController.navigate("signin/$it")
                },
                onNavigateToSignUp = {
                    navController.navigate("signup/$it")
                },
                onSignInAsGuest = {
                    UserRepository.signInAsGuest()
                    navController.navigate(Destinations.SURVEY_ROUTE)
                },
            )
        }

        composable(Destinations.SIGN_IN_ROUTE) {
            val startingEmail = it.arguments?.getString("email")
            val coroutineScope = rememberCoroutineScope()

            SignInRoute(
                email = startingEmail,
                onSignInSubmitted = { email: String, password: String ->
                    coroutineScope.launch {
                        val success = UserRepository.signIn(email, password)
                        Log.i("SurveyNavigation", "logged in Successful? $success")
                        if (success) {
                            Log.i("SurveyNavigation", "Begin Sign in")
                            when (UserRepository.user) {
                                is User.LoggedInNewUser -> {
                                    // TO DO:
                                    // Amplify
                                    navController.navigate(Destinations.SURVEY_ROUTE)
                                }
                                is User.LoggedInOldUser -> {
                                    navController.navigate(Destinations.MAIN_SCREEN_ROUTE)
                                }
                                else -> {
                                    // Handle unexpected user state
                                }
                            }
                            Log.i("SurveyNavigation", "End Sign in")
                        } else {
                            // Show error or handle unsuccessful sign-in
                        }
                    }
                },
                onSignInAsGuest = {
                    UserRepository.signInAsGuest()
                    navController.navigate(Destinations.SURVEY_ROUTE)
                },
                onNavUp = navController::navigateUp,
            )
        }


        composable(Destinations.SIGN_UP_ROUTE) {
            val startingEmail = it.arguments?.getString("email")
            SignUpRoute(
                email = startingEmail,
                onSignUpSubmitted = { email: String, password: String, success: Boolean ->
                    if (success) {
                        navController.navigate("signin/$email")
                    } else {
                        // Show error or handle unsuccessful sign-up
                    }
                },
                onSignInAsGuest = {
                    UserRepository.signInAsGuest()
                    navController.navigate(Destinations.SURVEY_ROUTE)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(Destinations.SURVEY_ROUTE) {
            SurveyRoute(
                onSurveyComplete = {
                    navController.navigate(Destinations.SURVEY_RESULTS_ROUTE)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(Destinations.SURVEY_RESULTS_ROUTE) {
            val coroutineScope = rememberCoroutineScope()
            SurveyResultScreen(
                onDonePressed = {
                    coroutineScope.launch {
                        UserRepository.setUserToOldUser()
                        navController.navigate(Destinations.MAIN_SCREEN_ROUTE) {
                            popUpTo(Destinations.SURVEY_ROUTE) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Destinations.MAIN_SCREEN_ROUTE) {
            // just sets up bottom navigation and calls LoginApplication()
            // function definition for LoginApplication() in MainNavigation.kt
            MainScreenView()
        }

    }
}

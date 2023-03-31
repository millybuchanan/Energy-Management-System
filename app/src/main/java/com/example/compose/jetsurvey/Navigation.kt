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

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.jetsurvey.Destinations.DASHBOARD_ROUTE
import com.example.compose.jetsurvey.Destinations.FEEDBACK_ROUTE
import com.example.compose.jetsurvey.Destinations.PREFERENCES_ROUTE
import com.example.compose.jetsurvey.Destinations.PROFILE_ROUTE
import com.example.compose.jetsurvey.Destinations.SIGN_IN_ROUTE
import com.example.compose.jetsurvey.Destinations.SIGN_UP_ROUTE
import com.example.compose.jetsurvey.Destinations.SURVEY_RESULTS_ROUTE
import com.example.compose.jetsurvey.Destinations.SURVEY_ROUTE
import com.example.compose.jetsurvey.Destinations.WELCOME_ROUTE
import com.example.compose.jetsurvey.screens.DashboardScreen
import com.example.compose.jetsurvey.screens.PreferencesScreen
import com.example.compose.jetsurvey.signinsignup.SignInRoute
import com.example.compose.jetsurvey.signinsignup.SignUpRoute
import com.example.compose.jetsurvey.signinsignup.WelcomeRoute
import com.example.compose.jetsurvey.survey.SurveyResultScreen
import com.example.compose.jetsurvey.survey.SurveyRoute

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SIGN_UP_ROUTE = "signup/{email}"
    const val SIGN_IN_ROUTE = "signin/{email}"
    const val SURVEY_ROUTE = "survey"
    const val SURVEY_RESULTS_ROUTE = "surveyresults"

    const val DASHBOARD_ROUTE = "dashboard"
    const val PREFERENCES_ROUTE = "preferences"
    const val FEEDBACK_ROUTE = "feedback"
    const val PROFILE_ROUTE = "profile"
}

@Composable
fun JetsurveyNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = WELCOME_ROUTE,
    ) {
        composable(WELCOME_ROUTE) {
            WelcomeRoute(
                onNavigateToSignIn = {
                    navController.navigate("signin/$it")
                },
                onNavigateToSignUp = {
                    navController.navigate("signup/$it")
                },
                onSignInAsGuest = {
                    navController.navigate(SURVEY_ROUTE)
                },
            )
        }

        composable(SIGN_IN_ROUTE) {
            val startingEmail = it.arguments?.getString("email")
            SignInRoute(
                email = startingEmail,
                onSignInSubmitted = {
                    navController.navigate(SURVEY_ROUTE)
                },
                onSignInAsGuest = {
                    navController.navigate(SURVEY_ROUTE)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(SIGN_UP_ROUTE) {
            val startingEmail = it.arguments?.getString("email")
            SignUpRoute(
                email = startingEmail,
                onSignUpSubmitted = {
                    navController.navigate(SURVEY_ROUTE)
                },
                onSignInAsGuest = {
                    navController.navigate(SURVEY_ROUTE)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(SURVEY_ROUTE) {
            SurveyRoute(
                onSurveyComplete = {
                    navController.navigate(SURVEY_RESULTS_ROUTE)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(SURVEY_RESULTS_ROUTE) {
            SurveyResultScreen(
                onDonePressed = {
                    navController.navigate(DASHBOARD_ROUTE) {
                        popUpTo(SURVEY_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(DASHBOARD_ROUTE) {
            DashboardScreen(name = "Name")
        }

        composable(PREFERENCES_ROUTE) {
            PreferencesScreen()
        }

        composable(FEEDBACK_ROUTE) {
//            FeedbackScreen()
        }

        composable(PROFILE_ROUTE) {
//            ProfileScreen()
        }

    }
}

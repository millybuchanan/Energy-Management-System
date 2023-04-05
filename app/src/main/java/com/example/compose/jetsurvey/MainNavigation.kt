package com.example.compose.jetsurvey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.jetsurvey.profile.FeedbackQuestionPreview
import com.example.compose.jetsurvey.profile.ProfilePagePreview
import com.example.compose.jetsurvey.screens.DashboardScreen
import com.example.compose.jetsurvey.screens.PreferencesScreen

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
    NavHost(
        navController = navController,
        startDestination = Menu_Destinations.DASHBOARD_ROUTE,
    ) {

        composable(Menu_Destinations.DASHBOARD_ROUTE) {
            DashboardScreen(name = "Name")
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
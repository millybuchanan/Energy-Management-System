package com.example.compose.jetsurvey.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compose.jetsurvey.Menu_Destinations


@Composable
fun BottomNavigationMenu(navController: NavHostController) {
    val items = listOf(
        Menu_Destinations.DASHBOARD_ROUTE,
        Menu_Destinations.PREFERENCES_ROUTE,
        Menu_Destinations.FEEDBACK_ROUTE,
        Menu_Destinations.PROFILE_ROUTE
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        Menu_Destinations.DASHBOARD_ROUTE -> Icon(Icons.Default.Home, contentDescription = "Dashboard")
                        Menu_Destinations.PREFERENCES_ROUTE -> Icon(Icons.Default.Settings, contentDescription = "Preferences")
                        Menu_Destinations.FEEDBACK_ROUTE -> Icon(Icons.Default.Feedback, contentDescription = "Feedback")
                        Menu_Destinations.PROFILE_ROUTE -> Icon(Icons.Default.AccountBox, contentDescription = "Profile")
                    }
                },
                selected = currentRoute == item,
                onClick = {
                    navController.navigate(item) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

package com.example.compose.jetsurvey.screens//package com.example.compose.jetsurvey.screens
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.material.icons.filled.Feedback
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.BottomNavigation
//import androidx.compose.material3.BottomNavigationItem
//import androidx.compose.material3.Icon
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.example.compose.jetsurvey.Destinations
//
//
//@Composable
//fun BottomNavigationMenu(navController: NavHostController) {
//    val items = listOf(
//        Destinations.DASHBOARD_ROUTE,
//        Destinations.PREFERENCES_ROUTE,
//        Destinations.FEEDBACK_ROUTE,
//        Destinations.PROFILE_ROUTE
//    )
//
//    BottomNavigation {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        items.forEach { item ->
//            BottomNavigationItem(
//                icon = {
//                    when (item) {
//                        Destinations.DASHBOARD_ROUTE -> Icon(Icons.Default.Home, contentDescription = "Dashboard")
//                        Destinations.PREFERENCES_ROUTE -> Icon(Icons.Default.Settings, contentDescription = "Preferences")
//                        Destinations.FEEDBACK_ROUTE -> Icon(Icons.Default.Feedback, contentDescription = "Feedback")
//                        Destinations.PROFILE_ROUTE -> Icon(Icons.Default.AccountBox, contentDescription = "Profile")
//                    }
//                },
//                selected = currentRoute == item,
//                onClick = {
//                    navController.navigate(item) {
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}

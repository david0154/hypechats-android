package com.nexuzy.hypechats.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nexuzy.hypechats.ui.screens.LoginScreen
import com.nexuzy.hypechats.ui.screens.FeedScreen
import com.nexuzy.hypechats.ui.screens.ProfileScreen

@Composable
fun HypechatsNavigation(navController: NavController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("feed") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("feed") {
            FeedScreen(
                onProfileClick = { userId ->
                    navController.navigate("profile/$userId")
                }
            )
        }

        composable("profile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            ProfileScreen(
                userId = userId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

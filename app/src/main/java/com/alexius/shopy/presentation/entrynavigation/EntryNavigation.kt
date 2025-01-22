package com.alexius.shopy.presentation.entrynavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.util.Route
import com.alexius.shopy.presentation.onboarding.OnboardingScreen

@Composable
fun EntryNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.OnboardingScreen.route,
    ) {
        composable(Route.OnboardingScreen.route) {
            // OnboardingScreen()
            OnboardingScreen(
                onSignInClick = {
                    navigateTo(navController, Route.SignInScreen.route)
                },
                onSignUpClick = {
                    navigateTo(navController, Route.SignUpScreen.route)
                }
            )
        }
        composable(Route.SignInScreen.route) {
            // SignInScreen()
        }
    }
}

private fun navigateTo(navController: NavController, route: String){
    navController.navigate(route){
        launchSingleTop = true
    }
}
package com.alexius.core.util

sealed class Route(val route: String) {

    object MainNavigation : Route(route = "mainNavigation")

    object EntryNavigation : Route(route = "entryNavigation")

    object OnboardingScreen : Route(route = "onboardingScreen")

    object SignInScreen : Route(route = "signInScreen")

    object SignUpScreen : Route(route = "signUpScreen")

    object HomeScreen : Route(route = "homeScreen")
}
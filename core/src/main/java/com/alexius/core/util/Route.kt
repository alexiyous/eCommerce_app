package com.alexius.core.util

sealed class Route(val route: String) {

    object MainNavigation : Route(route = "mainNavigation")

    object EntryNavigation : Route(route = "entryNavigation")

    object OnboardingScreen : Route(route = "onboardingScreen")

    object SignInScreen : Route(route = "signInScreen")

    object ResetPasswordScreen : Route(route = "resetPasswordScreen")

    object SignUpScreen : Route(route = "signUpScreen")

    object HomeScreen : Route(route = "homeScreen")

    object SearchScreen : Route(route = "searchScreen")

    object CheckOutScreen : Route(route = "checkOutScreen")

    object ProfileScreen : Route(route = "profileScreen")

    object DetailProductScreen : Route(route = "detailProductScreen")

    object ListProductScreen : Route(route = "listProductScreen")

    object SettingScreen : Route(route = "settingScreen")
}
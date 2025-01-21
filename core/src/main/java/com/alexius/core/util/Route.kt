package com.alexius.core.util

sealed class Route(val route: String) {

    object HomeScreen : Route(route = "homeScreen")
}
package com.alexius.shopy.presentation

import com.alexius.core.util.Route

data class MainState(
    val splashCondition: Boolean = true,
    val startDestination: String = Route.EntryNavigation.route
)

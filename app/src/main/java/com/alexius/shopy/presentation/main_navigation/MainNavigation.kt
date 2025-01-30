package com.alexius.shopy.presentation.main_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexius.core.util.Route
import com.alexius.shopy.presentation.main_navigation.components.AnimatedNavigationBar
import com.alexius.shopy.presentation.main_navigation.components.ButtonData

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
) {
    val bottomNavItems = remember{
        listOf(
            ButtonData("Home", Icons.Default.Home),
            ButtonData("Search", Icons.Default.Search),
            ButtonData("Check Out", Icons.Default.ShoppingBag),
            ButtonData("Profile", Icons.Default.Person)
        )
    }

    val navController = rememberNavController()

    val backstackState = navController.currentBackStackEntryAsState().value

    var selectedItem by remember{
        mutableIntStateOf(0)
    }

    selectedItem = remember(backstackState) {
        when(backstackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.CheckOutScreen.route -> 2
            Route.ProfileScreen.route -> 3
            else -> 0
        }
    }

    val isBottomBarVisible = remember {
        derivedStateOf {
            backstackState?.destination?.route in listOf(
                Route.HomeScreen.route,
                Route.SearchScreen.route,
                Route.CheckOutScreen.route,
                Route.ProfileScreen.route
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible.value){
                AnimatedNavigationBar(
                    buttons = bottomNavItems,
                    barColor = MaterialTheme.colorScheme.surface,
                    circleColor = MaterialTheme.colorScheme.primary,
                    selectedColor = MaterialTheme.colorScheme.onSurface,
                    unselectedColor = Color.Gray,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateTo(navController, Route.HomeScreen.route)
                            1 -> navigateTo(navController, Route.SearchScreen.route)
                            2 -> navigateTo(navController, Route.CheckOutScreen.route)
                            3 -> navigateTo(navController, Route.ProfileScreen.route)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = modifier.padding(innerPadding)
        ){
            composable(Route.HomeScreen.route){
                // HomeScreen()
            }
            composable(Route.SearchScreen.route){
                // SearchScreen()
            }
            composable(Route.CheckOutScreen.route){
                // CheckOutScreen()
            }
            composable(Route.ProfileScreen.route){
                // ProfileScreen()
            }
        }
    }

}

private fun navigateTo(navController: NavController, route: String){
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            // Reuse the existing instance of the composable (case: if user taps on the same tab,
            // it will not create a new instance)
            launchSingleTop = true
        }
    }
}
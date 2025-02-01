package com.alexius.shopy.presentation.main_navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexius.core.domain.model.Product
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.core.util.Route
import com.alexius.shopy.presentation.commons.LoadingScreen
import com.alexius.shopy.presentation.home.HomeScreen
import com.alexius.shopy.presentation.home.HomeViewModel
import com.alexius.shopy.presentation.main_navigation.components.AnimatedNavigationBar
import com.alexius.shopy.presentation.main_navigation.components.ButtonData
import com.alexius.shopy.presentation.profile.ProfileScreen
import com.alexius.shopy.presentation.profile.ProfileScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    var isLoading by remember {
        mutableStateOf(false)
    }

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

    val isBottomBarVisible = remember(backstackState) {
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
        modifier = modifier,
        bottomBar = {
            AnimatedNavigationBar(
                isBottomBarVisible = isBottomBarVisible.value,
                buttons = bottomNavItems,
                barColor = MaterialTheme.colorScheme.primaryContainer,
                circleColor = MaterialTheme.colorScheme.surfaceVariant,
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onSurface,
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = modifier.padding(innerPadding)
        ){
            composable(Route.HomeScreen.route){
                val viewModel: HomeViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val newUserInfo = navController.currentBackStackEntry?.savedStateHandle?.get<UserInfoDomain?>("newUserInfo")

                LaunchedEffect(state.userInfo) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("userInfo", if (newUserInfo != null) newUserInfo else state.userInfo)
                }

                val featuredProducts = remember(state.products){
                    state.products.shuffled().take(5)
                }

                val popularProducts = remember(state.products){
                    state.products.shuffled().take(5)
                }

                HomeScreen(
                    onProfileSectionClicked = {
                        navigateTo(navController, Route.ProfileScreen.route)
                    },
                    imageProfile = newUserInfo?.profileImage ?: state.userInfo.profileImage,
                    nameProfile = state.userInfo.name,
                    isLoading = state.isLoading,
                    featuredProducts = featuredProducts,
                    onProductClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("product", it)
                        navigateTo(navController, Route.DetailProductScreen.route)
                    },
                    onFeatureProductSeeAllClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("productList", state.products.shuffled())
                        navigateTo(navController, Route.ListProductScreen.route)
                    },
                    popularProducts = popularProducts,
                    onPopularProductSeeAllClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("productList", state.products.shuffled())
                        navigateTo(navController, Route.ListProductScreen.route)
                    }
                )
            }
            composable(Route.SearchScreen.route){
                // SearchScreen()
            }
            composable(Route.CheckOutScreen.route){
                // CheckOutScreen()
            }
            composable(Route.ProfileScreen.route){
                val userInfo = navController.previousBackStackEntry?.savedStateHandle?.get<UserInfoDomain?>("userInfo")
                val viedModel: ProfileScreenViewModel = koinViewModel()
                val state by viedModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(userInfo) {
                    viedModel.getUserInfoForViewModel(userInfo ?: UserInfoDomain(
                        email = "",
                        name = "",
                        profileImage = ""
                    ))
                }

                ProfileScreen(
                    userInfo = viedModel.state.value.userInfo ?: UserInfoDomain(
                        email = "",
                        name = "",
                        profileImage = ""
                    ),
                    onProfileNameChange = {
                        viedModel.updateNameInUserInfo(it)
                    },
                    onUploadingNewProfilePic = {
                        isLoading = it
                    },
                    isLoading = state.isLoading,
                    onNameSubmit = { onSuccess, onError ->
                        viedModel.updateUserName(
                            onSuccess = onSuccess,
                            onError = onError
                        )
                    },
                    onCropSuccess = {
                        viedModel.uploadProfileImage(it,
                            onSuccessUploading = {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "newUserInfo",
                                    viedModel.state.value.userInfo?.copy(profileImage = it)
                                )
                            },
                            onError = {
                                // Show Toast
                                Toast.makeText(
                                    context,
                                    "Upload Image Failed: $it",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    onSettingOptionClick = {
                        navController.navigate(Route.SettingScreen.route){
                            launchSingleTop = true
                        }
                    },
                    onShareOptionClick = TODO(),
                    onSignOut = TODO(),
                )
            }

            composable(Route.DetailProductScreen.route){
                val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
            }

            composable(Route.ListProductScreen.route){
                val products = navController.previousBackStackEntry?.savedStateHandle?.get<List<Product>>("productList")

            }

            composable(Route.SettingScreen.route){
                // SettingScreen()
            }
        }
    }

    if (isLoading){
        LoadingScreen()
    }

}

///Use this function ONLY to navigate in main navigation inside the Bottom Navigation Bar
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
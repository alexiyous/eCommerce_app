package com.alexius.shopy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.util.Route
import com.alexius.shopy.presentation.entrynavigation.EntryNavigation
import com.alexius.shopy.presentation.home.HomeScreen
import com.alexius.shopy.presentation.ui.theme.ShopyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { viewModel.state.value.splashCondition })
        }
        enableEdgeToEdge()
        setContent {
            ShopyTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = state.startDestination){
                    composable(route = Route.EntryNavigation.route){
                        EntryNavigation()
                    }

                    composable(route = Route.MainNavigation.route){
                        Greeting(name = "Shopy")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
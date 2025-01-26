package com.alexius.shopy.presentation.entrynavigation

import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.util.Route
import com.alexius.shopy.presentation.onboarding.OnboardingScreen
import com.alexius.shopy.presentation.sign_in.SignInScreen
import com.alexius.shopy.presentation.sign_in.SignInViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alexius.shopy.presentation.resetpass.ResetPassScreen
import com.alexius.shopy.presentation.resetpass.ResetPassViewModel

@Composable
fun EntryNavigation(
    modifier: Modifier = Modifier,
    onEndEntryNavigation: () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current

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
        composable(Route.SignUpScreen.route) {
            // SignUpScreen()
        }
        composable(Route.SignInScreen.route) {
            val viewModel: SignInViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            SignInScreen(
                onBackClick = {
                    navigateTo(navController, Route.OnboardingScreen.route)
                },
                email = state.email,
                onEmailValueChange = {
                    viewModel.updateEmail(it)
                },
                emailInputFieldError = state.emailIsError,
                password = state.password,
                onPasswordValueChange = {
                    viewModel.updatePassword(it)
                },
                passwordInputFieldError = state.passwordIsError,
                onForgotPasswordClick = {
                    navigateTo(navController, Route.ResetPasswordScreen.route)
                },
                onSignInClick = {
                    viewModel.signIn(
                        onSignInSuccess = {
                            onEndEntryNavigation()
                        },
                        onSignInFailed = {
                            // Show Toast
                            Toast.makeText(
                                context,
                                "Sign In Failed: $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                mainButtonEnable = !state.emailIsError && !state.passwordIsError,
                isLoading = state.isLoading
            )
        }
        composable(Route.ResetPasswordScreen.route) {
            // ResetPasswordScreen()
            val viewModel: ResetPassViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ResetPassScreen(
                onBackClick = {
                    navigateTo(navController, Route.SignInScreen.route)
                },
                email = state.email,
                onEmailChange = {
                    viewModel.updateEmail(it)
                },
                emailInputFieldError = state.emailIsError,
                mainButtonEnable = !state.emailIsError,
                onSendClick = {
                    viewModel.resetPass(
                        callbackOnsuccess = {
                            val intent = Intent(Intent.ACTION_MAIN).apply {
                                addCategory(Intent.CATEGORY_APP_EMAIL)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }

                            // Check if there's an email app available
                            if (intent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(
                                    context,
                                    "No email app found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        callbackOnFailed = {
                            Toast.makeText(
                                context,
                                "Reset Password Failed: $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            )
        }
    }
}

private fun navigateTo(navController: NavController, route: String){
    navController.navigate(route){
        launchSingleTop = true
    }
}
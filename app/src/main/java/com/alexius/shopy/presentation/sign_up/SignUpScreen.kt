package com.alexius.shopy.presentation.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.shopy.presentation.commons.LoadingScreen
import com.alexius.shopy.presentation.commons.MainButton
import com.alexius.shopy.presentation.commons.TopBar
import com.alexius.shopy.presentation.sign_in.components.EmailInputField
import com.alexius.shopy.presentation.sign_in.components.PasswordInputField
import com.alexius.shopy.presentation.sign_up.components.NameInputField
import com.alexius.shopy.presentation.sign_up.components.PasswordSignUpField
import com.alexius.shopy.presentation.ui.theme.ShopyTheme

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    name: String,
    onNameValueChange: (String) -> Unit,
    nameIsError: Boolean,
    email: String,
    onEmailValueChange: (String) -> Unit,
    emailIsError: Boolean,
    password: String,
    onPasswordValueChange: (String) -> Unit,
    passwordIsError: Boolean,
    containOneLowerCase: Boolean,
    containOneUpperCase: Boolean,
    containOneDigit: Boolean,
    containOneSpecialChar: Boolean,
    containSixChars: Boolean,
    onAlreadyHaveAccountClick: () -> Unit,
    onSignUpClick: () -> Unit,
    mainButtonEnable: Boolean,
    isLoading: Boolean
) {

    if (isLoading) {
        LoadingScreen()
        return
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ){innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = modifier.height(149.dp))

            NameInputField(
                name = name,
                onNameChange = onNameValueChange,
                isError = nameIsError
            )
            Spacer(modifier = modifier.height(12.dp))
            EmailInputField(
                email = email,
                onValueChange = onEmailValueChange,
                isError = emailIsError
            )
            Spacer(modifier = modifier.height(12.dp))
            PasswordSignUpField(
                password = password,
                onValueChange = onPasswordValueChange,
                isError = passwordIsError,
                containOneLowerCase = containOneLowerCase,
                containOneUpperCase = containOneUpperCase,
                containOneDigit = containOneDigit,
                containOneSpecialChar = containOneSpecialChar,
                containSixChars = containSixChars
            )
            Spacer(modifier = modifier.height(16.dp))
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Row( modifier = modifier.clickable { onAlreadyHaveAccountClick() }) {

                    Text(
                        text = "Already have an account?",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = modifier.height(32.dp))
            MainButton(
                text = "SIGN UP",
                onClick = onSignUpClick,
                enabled = mainButtonEnable
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopyTheme {
        SignUpScreen(
            name = "",
            onNameValueChange = {},
            nameIsError = false,
            email = "",
            onEmailValueChange = {},
            emailIsError = false,
            password = "",
            onPasswordValueChange = {},
            passwordIsError = false,
            containOneLowerCase = false,
            containOneUpperCase = false,
            containOneDigit = false,
            containOneSpecialChar = false,
            containSixChars = false,
            onBackClick = {},
            onAlreadyHaveAccountClick = {},
            onSignUpClick = {},
            mainButtonEnable = false,
            isLoading = false
        )
    }
}
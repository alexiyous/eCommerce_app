package com.alexius.shopy.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexius.shopy.presentation.commons.TopBar
import com.alexius.shopy.presentation.sign_in.components.EmailInputField
import com.alexius.shopy.presentation.sign_in.components.PasswordInputField

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    email: String,
    onEmailValueChange: (String) -> Unit,
    emailInputFieldError: Boolean,
    password: String,
    onPasswordValueChange: (String) -> Unit,
    passwordInputFieldError: Boolean
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = {
                    Text(
                        text = "Sign In",
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
    ) { innerPadding ->
        Column(
            modifier = modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = modifier.height(149.dp))
            EmailInputField(
                email = email,
                onValueChange = onEmailValueChange,
                isError = emailInputFieldError
            )
            Spacer(modifier = modifier.height(8.dp))
            PasswordInputField(
                password = password,
                onValueChange = onPasswordValueChange,
                isError = passwordInputFieldError
            )
        }
    }
}
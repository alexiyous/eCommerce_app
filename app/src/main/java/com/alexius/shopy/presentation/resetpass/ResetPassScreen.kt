package com.alexius.shopy.presentation.resetpass

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.shopy.presentation.commons.MainButton
import com.alexius.shopy.presentation.commons.TopBar
import com.alexius.shopy.presentation.sign_in.components.EmailInputField
import com.alexius.shopy.presentation.ui.theme.ShopyTheme

@Composable
fun ResetPassScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    emailInputFieldError: Boolean,
    mainButtonEnable: Boolean,
    onSendClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = {
                    Text(
                        text = "Reset Password",
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
    ){ innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Write your email to be sent a link to reset your password and check your inbox.",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(24.dp))
                EmailInputField(
                    email = email,
                    onValueChange = onEmailChange,
                    isError = emailInputFieldError
                )
                Spacer(modifier = modifier.height(24.dp))
                MainButton(
                    text = "SEND",
                    onClick = onSendClick,
                    enabled = mainButtonEnable
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopyTheme() {

        ResetPassScreen(
            email = "",
            onEmailChange = {},
            emailInputFieldError = false,
            mainButtonEnable = true,
            onSendClick = {},
            onBackClick = {}
        )
    }
}
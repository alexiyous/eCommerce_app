package com.alexius.shopy.presentation.sign_in.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    password: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = MaterialTheme.shapes.small
    ) {
        TextField(
            value = password,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            },
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            isError = isError,
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = {
                if (!isError){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = Color(0xFF2AA952),
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}
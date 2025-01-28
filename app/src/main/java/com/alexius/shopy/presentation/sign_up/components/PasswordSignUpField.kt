package com.alexius.shopy.presentation.sign_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.alexius.shopy.presentation.ui.theme.bodyFontFamily

@Composable
fun PasswordSignUpField(
    modifier: Modifier = Modifier,
    password: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    containOneLowerCase: Boolean,
    containOneUpperCase: Boolean,
    containOneDigit: Boolean,
    containOneSpecialChar: Boolean,
    containSixChars: Boolean
) {

    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Card(
            modifier = modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            shape = MaterialTheme.shapes.small,
        ) {
            TextField(
                value = password,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                label = {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = modifier.fillMaxWidth(),
                isError = isError,
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val (icon, iconColor) = if (showPassword) {
                            Pair(
                                Icons.Filled.Visibility,
                                second = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Pair(Icons.Filled.VisibilityOff, MaterialTheme.colorScheme.onSurface)
                        }

                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                icon,
                                contentDescription = "Visibility",
                                tint = iconColor
                            )
                        }

                        if (!isError) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "",
                                tint = Color(0xFF2AA952),
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (!containOneLowerCase) {
            Text(
                text = "Contain at least one lower case letter",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!containOneUpperCase) {
            Text(
                text = "Contain at least one upper case letter",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!containOneDigit) {
            Text(
                text = "Contain at least one digit",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!containOneSpecialChar) {
            Text(
                text = "Contain at least one special character",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!containSixChars) {
            Text(
                text = "Contain at least six characters",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
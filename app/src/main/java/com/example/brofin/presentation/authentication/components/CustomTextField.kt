package com.example.brofin.presentation.authentication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    label: String,
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    isPassword: Boolean = false,
    validate: (String) -> String ,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                if (isPassword) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = IdentifierTextField.Password)
                }
                if (label == IdentifierTextField.Email) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = IdentifierTextField.Email)
                }
                if (label == IdentifierTextField.Name) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = IdentifierTextField.Name)
                }
                if (label == IdentifierTextField.ConfirmPassword) {
                    Icon(imageVector = Icons.Default.LockOpen, contentDescription = IdentifierTextField.ConfirmPassword)

                }
            },
            label = { Text(label) },
            singleLine = true,
            onValueChange = { newValue ->
                onTextChange(newValue)
                errorMessage = validate(newValue)
                isError = errorMessage.isNotEmpty()
            },
            isError = isError,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector =  if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Lihat Password" else "Sembunyikan Password"
                        )

                    }
                }
            },

            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
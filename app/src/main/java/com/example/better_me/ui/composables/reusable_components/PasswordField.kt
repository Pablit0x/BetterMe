package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.better_me.R
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White

/**
 * This composable function contains the field that lets users enter their password
 * @param password Mutable String variable that represents the password inside the input field
 * @param isError The mutable Boolean variable that represents the error state of the PasswordField
 */
@Composable
fun PasswordField(password: MutableState<String>, isError: MutableState<Boolean>) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = password.value,
        onValueChange = { passwordValue ->
            password.value = passwordValue
            isError.value = false
        },
        trailingIcon = {

            if (isError.value)
                Icon(Icons.Filled.Warning, "error icon", tint = MaterialTheme.colors.error)
            else {

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_eye),
                        "password visibility identifier",
                        tint = if (passwordVisibility) White else Color.DarkGray
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock_closed),
                "lock icon",
                tint = White
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.password),
                color = Color.LightGray
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = PerfectGray,
            textColor = White
        ),
        keyboardOptions =
        KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onAny = { focusManager.clearFocus() }
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
    )

    if (isError.value) {
        Text(
            text = stringResource(id = R.string.invalid_password_msg),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}
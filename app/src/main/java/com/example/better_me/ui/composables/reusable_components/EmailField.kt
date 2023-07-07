package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.better_me.R
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White

/**
 * This composable function contains the field that lets users enter an email address.
 * @param email Mutable String variable that represents the email inside the input field
 * @param isError The mutable Boolean variable that represents the error state of the EmailField
 */
@Composable
fun EmailField(email: MutableState<String>, isError: MutableState<Boolean>) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = email.value,
        onValueChange = { emailValue ->
            email.value = emailValue
            isError.value = false
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.email),
                color = Color.LightGray
            )
        },
        keyboardOptions =
        KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onAny = { focusManager.clearFocus() }
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f),
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            backgroundColor = PerfectGray
        ),
        trailingIcon = {
            if (isError.value)
                Icon(Icons.Filled.Warning, "error icon", tint = MaterialTheme.colors.error)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                "email Icon", tint = White
            )
        },
    )
    if (isError.value) {
        Text(
            text = stringResource(id = R.string.invalid_email_msg),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}
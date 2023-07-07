package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.toSize
import com.example.better_me.R
import com.example.better_me.data.model.ActivityType
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White

/**
 * This composable function contains the field which lets a user select the activity type.
 * @param activityType Mutable String variable that represents the selected by the user activity type
 * @param isError The mutable Boolean variable that represents the error state of the ActivityTypeField
 */
@Composable
fun ActivityTypeField(activityType: MutableState<String>, isError: MutableState<Boolean>) {
    var expanded by remember { mutableStateOf(false) }


    val activityTypes = listOf(
        ActivityType.Sleep.name,
        ActivityType.Work.name,
        ActivityType.Study.name,
        ActivityType.Exercise.name,
        ActivityType.Entertainment.name,
        ActivityType.Other.name
    )

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val arrowIcon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        TextField(
            value = activityType.value,
            onValueChange = { accountTypeValue ->
                activityType.value = accountTypeValue
                isError.value = false
            },
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = White,
                backgroundColor = PerfectGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned
                { coords ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coords.size.toSize()
                },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.select_activity_type),
                    color = Color.LightGray
                )
            },
            trailingIcon = {
                Icon(
                    arrowIcon, "arrow icon",
                    Modifier.clickable { expanded = !expanded }, tint = Color.LightGray
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            activityTypes.forEach { name ->
                DropdownMenuItem(onClick = {
                    activityType.value = name
                    expanded = false
                }) {
                    Text(text = name, textAlign = TextAlign.Center)
                }
            }
        }
        if (isError.value) {
            Text(
                text = stringResource(id = R.string.invalid_activity_type),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}
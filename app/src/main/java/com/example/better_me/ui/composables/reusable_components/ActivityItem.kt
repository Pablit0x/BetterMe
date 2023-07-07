package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.better_me.R
import com.example.better_me.data.model.Activity
import com.example.better_me.data.model.Constants.REGULAR_USER
import com.example.better_me.data.model.Constants.SUPERVISOR_USER
import com.example.better_me.ui.composables.screens.regular.main.RegularMainViewModel
import com.example.better_me.ui.theme.*

/**
 * This composable function represents how each activity item inside the lazy column will look.
 * @param activity Activity object that will be displayed
 * @param date Date to which this activity item was assigned
 * @param userType User type identifier to determine what functionalities should each activity item contain
 */
@Composable
fun ActivityItem(activity: Activity, date: String, userType: Int) {
    var style by remember { mutableStateOf(TextStyle(textDecoration = TextDecoration.None)) }
    var bgColor by remember { mutableStateOf(Color.White) }
    var checkState by remember { mutableStateOf(activity.done.toString().toBoolean()) }
    var activityIcon by remember { mutableStateOf(R.drawable.ic_other_activities) }
    var contentColor by remember { mutableStateOf(Color.White) }
    val regularMainViewModel = RegularMainViewModel()

    when (activity.type) {
        stringResource(id = R.string.study) -> activityIcon = R.drawable.ic_study
        stringResource(id = R.string.sleep) -> activityIcon = R.drawable.ic_sleep
        stringResource(id = R.string.work) -> activityIcon = R.drawable.ic_work
        stringResource(id = R.string.exercise) -> activityIcon = R.drawable.ic_exercise
        stringResource(id = R.string.entertainment) -> activityIcon = R.drawable.ic_entertainment
        stringResource(id = R.string.other) -> activityIcon - R.drawable.ic_other_activities
    }

    when (activity.createdBy) {
        REGULAR_USER -> bgColor = White
        SUPERVISOR_USER -> bgColor = DeltaPaleYellow
    }
    when (checkState) {
        true -> {
            style = TextStyle(textDecoration = TextDecoration.LineThrough)
            bgColor = WageningenGreen
            contentColor = White
        }
        false -> {
            style = TextStyle(textDecoration = TextDecoration.None)
            contentColor = Black
        }
    }
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(5.dp)
            .border(width = 1.dp, color = White, shape = RoundedCornerShape(12.dp)),
        color = bgColor
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = (painterResource(id = activityIcon)),
                    contentDescription = "activity icon",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .size(
                            IconSizes.medium
                        ),
                    tint = contentColor
                )
                Text(
                    text = activity.name.toString(),
                    color = contentColor,
                    textAlign = TextAlign.Start,
                    style = style,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp)
                )
                if (userType == REGULAR_USER) {
                    Checkbox(
                        checked = checkState, onCheckedChange = {
                            checkState = it
                            regularMainViewModel.setActivityStatus(
                                checkState,
                                activity,
                                date = date
                            )
                        }, modifier = Modifier
                            .scale(1.5f)
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = CheckboxDefaults.colors(
                            White,
                            uncheckedColor = Black
                        )
                    )
                }
            }
        }
    }
}
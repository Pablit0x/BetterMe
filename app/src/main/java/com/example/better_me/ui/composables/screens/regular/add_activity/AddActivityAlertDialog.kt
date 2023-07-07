package com.example.better_me.ui.composables.screens.regular.add_activity

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.example.better_me.R
import com.example.better_me.data.model.Activity
import com.example.better_me.data.model.Constants.REGULAR_USER
import com.example.better_me.data.model.Constants.SUPERVISOR_USER
import com.example.better_me.ui.composables.reusable_components.ActivityTypeField
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White
import java.util.*

/**
 * This function displays the alert dialog used to add new activities
 * @param isDialogOpen Mutable Boolean variable used to determine if the dialog window should be open
 * @param date String variable used to indicate to which day the activity will be attached
 * @param navController Navigation controller is used to navigate back to the correct screen
 * @param userType User type identifier to determine what options should the dialog contain
 * @param userID User ID is used to determine to which user the activity will be attached
 */

@Composable
fun ShowAddActivityAlertDialog(
    isDialogOpen: MutableState<Boolean>,
    date: String,
    userType: Int,
    userID: String,
    myCallback: (Activity) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val nameVal = remember { mutableStateOf("") }
    val activityType = remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }
    var privacyType by remember {
        mutableStateOf("Public")
    }
    val activityNameError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val activityTypeError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val addActivityViewModel = AddActivityViewModel()

    var dialogSize by remember { mutableStateOf(0.8f) }
    when (userType) {
        SUPERVISOR_USER -> dialogSize = 0.65f
        REGULAR_USER -> dialogSize = 0.8f
    }


    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(dialogSize)
                    .padding(5.dp)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = stringResource(id = R.string.add_activity),
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        value = nameVal.value,
                        onValueChange = {
                            nameVal.value = it
                            activityNameError.value = false
                        },
                        keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.clearFocus() }
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.enter_activity_name),
                                color = Color.LightGray
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = White,
                            backgroundColor = PerfectGray
                        ),
                        trailingIcon = {
                            if (activityNameError.value)
                                Icon(
                                    Icons.Filled.Warning,
                                    "error icon",
                                    tint = MaterialTheme.colors.error
                                )
                        }
                    )
                    if (activityNameError.value) {
                        Text(
                            text = stringResource(id = R.string.invalid_activity_name),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    ActivityTypeField(activityType = activityType, activityTypeError)
                    if (userType == REGULAR_USER) {

                        Spacer(modifier = Modifier.padding(8.dp))

                        var expanded by remember { mutableStateOf(false) }

                        val privacyTypes = listOf(
                            "Public",
                            "Private"
                        )

                        var textFieldSize by remember { mutableStateOf(Size.Zero) }

                        val arrowIcon = if (expanded)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        ) {
                            TextField(
                                value = privacyType,
                                onValueChange = { accountTypeValue ->
                                    privacyType = accountTypeValue
                                },
                                enabled = false,
                                leadingIcon = {
                                    when (privacyType) {
                                        "Public" -> Icon(
                                            painter = painterResource(id = R.drawable.ic_lock_open),
                                            "open lock icon", tint = White
                                        )
                                        "Private" -> Icon(
                                            painter = painterResource(id = R.drawable.ic_lock_closed),
                                            "closed lock icon", tint = White
                                        )
                                    }
                                },
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
                                trailingIcon = {
                                    Icon(
                                        arrowIcon,
                                        "arrow icon",
                                        Modifier.clickable { expanded = !expanded },
                                        tint = Color.LightGray
                                    )
                                }
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                            ) {
                                privacyTypes.forEach { name ->
                                    DropdownMenuItem(onClick = {
                                        privacyType = name
                                        expanded = false
                                    }) {
                                        Text(text = name, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Checkbox(
                                checked = isChecked.value,
                                onCheckedChange = { isChecked.value = it },
                                colors = CheckboxDefaults.colors(Color.White),
                                modifier = Modifier
                                    .scale(1.5f)
                                    .padding(end = 8.dp)
                            )

                            Text(text = stringResource(id = R.string.completed), fontSize = 20.sp)

                        }

                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                activityTypeError.value =
                                    !addActivityViewModel.validateActivityType(activityType = activityType.value)
                                activityNameError.value =
                                    !addActivityViewModel.validateActivityName(nameVal.value)
                                if (!activityNameError.value && !activityTypeError.value) {
                                    val uniqueID = UUID.randomUUID().toString()
                                    val activity = Activity(
                                        name = nameVal.value,
                                        type = activityType.value,
                                        done = isChecked.value,
                                        id = uniqueID,
                                        createdBy = userType,
                                        privacyType = privacyType
                                    )
                                    myCallback(activity)
                                    nameVal.value = String()
                                    activityType.value = String()
                                    isChecked.value = false
                                    privacyType = "Public"
                                    addActivityViewModel.addActivity(
                                        activity = activity,
                                        uid = userID,
                                        date = date
                                    )
                                    isDialogOpen.value = false
                                }
                            },
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth(0.5f)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(White)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add),
                                color = Black,
                                fontSize = 16.sp
                            )
                        }

                        Button(
                            onClick = {
                                isDialogOpen.value = false
                            },
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(White)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }

}

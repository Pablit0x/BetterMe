package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.better_me.R
import com.example.better_me.ui.composables.screens.supervisor.add_feedback.AddFeedbackViewModel
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White

/**
 * This function displays the alert dialog used to add feedback to the supervised user's daily record
 * @param isDialogOpen Mutable Boolean variable used to determine if the dialog window should be open
 * @param date String variable used to indicate to which day the feedback will be attached
 * @param feedbackComment Mutable String variable that represents the feedback comment inside the input field
 */

@Composable
fun AddFeedbackDialog(
    isDialogOpen: MutableState<Boolean>,
    date: String,
    userID: String,
    feedbackComment: MutableState<String>
) {
    val addFeedbackViewModel = AddFeedbackViewModel()
    if (isDialogOpen.value) {
        val focusManager = LocalFocusManager.current
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = White, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = stringResource(id = R.string.leave_feedback),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = White
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    TextField(
                        value = feedbackComment.value,
                        onValueChange = { text ->
                            feedbackComment.value = text
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = White,
                            backgroundColor = PerfectGray
                        ),
                        keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.clearFocus() }
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.enter_feedback),
                                color = Color.LightGray
                            )
                        },
                        modifier = Modifier.fillMaxHeight(0.8f)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))


                    Button(
                        onClick = {
                            isDialogOpen.value = false
                            addFeedbackViewModel.addFeedback(
                                uid = userID,
                                comment = feedbackComment.value,
                                date = date
                            )
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(White)
                    ) {
                        Text(
                            text = stringResource(id = R.string.submit),
                            fontSize = 16.sp,
                            color = Black
                        )
                    }
                }
            }

        }
    }
}
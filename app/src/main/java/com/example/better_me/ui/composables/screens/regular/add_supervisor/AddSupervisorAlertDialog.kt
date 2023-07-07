package com.example.better_me.ui.composables.screens.regular.add_supervisor

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.better_me.R
import com.example.better_me.ui.composables.reusable_components.EmailField
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.White

/**
 * This composable function displays the alert dialog used to add a new supervisor to the user
 * @param isDialogOpen Mutable Boolean variable used to determine if the dialog window should be open
 */
@Composable
fun ShowAddSupervisorDialog(isDialogOpen: MutableState<Boolean>) {
    val email = remember { mutableStateOf("") }
    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val addSupervisorViewModel = AddSupervisorViewModel()

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.60f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = White, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = stringResource(id = R.string.add_supervisor),
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    EmailField(email = email, isError = emailError)

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            emailError.value =
                                !addSupervisorViewModel.validateEmail(email = email.value)
                            if (!emailError.value) {
                                addSupervisorViewModel.addSupervisor(email = email.value)
                                isDialogOpen.value = false
                            }
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth(0.8f)
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(White)
                    ) {
                        Text(
                            text = stringResource(id = R.string.send_request),
                            color = Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

}

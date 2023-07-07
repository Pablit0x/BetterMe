package com.example.better_me.ui.composables.screens.select_avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.better_me.R
import com.example.better_me.ui.composables.reusable_components.avatarList
import com.example.better_me.ui.composables.screens.supervisor.main.setAvatarDialog
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.White


/**
 * This function displays the alert dialog used to display the avatar selection window
 * @param isDialogOpen Mutable Boolean variable used to determine if the dialog window should be open
 * @param navController Navigation controller is used to navigate back to the correct screen
 */

@Composable
fun ShowSelectAvatarDialog(
    isDialogOpen: MutableState<Boolean>,
    userType: Int,
    navController: NavHostController,
    myCallback: (Int) -> Unit
) {
    val selectAvatarViewModel = AvatarViewModel()
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = White, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.select_avatar),
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (index in 0..3) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                        .size(55.dp),
                                    shape = CircleShape,
                                    elevation = 2.dp
                                ) {
                                    val image: Painter = painterResource(id = avatarList[index])
                                    Image(painter = image, contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .size(55.dp)
                                            .clickable {
                                                selectAvatarViewModel.setAvatar(
                                                    userType = userType,
                                                    avatarID = index
                                                )
//                                when (userType) {
//                                    REGULAR_USER -> navController.navigate(
//                                        Screen.RegularMainScreen.route
//                                    )
//                                    SUPERVISOR_USER -> navController.navigate(
//                                        Screen.SupervisorMainScreen.route
//                                    )
//                                }
                                                myCallback(index)
                                                setAvatarDialog(avatarDialogState = false)
                                            })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (index in 4..7) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                        .size(55.dp),
                                    shape = CircleShape,
                                    elevation = 2.dp
                                ) {
                                    val image: Painter = painterResource(id = avatarList[index])
                                    Image(painter = image, contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .size(55.dp)
                                            .clickable {
                                                selectAvatarViewModel.setAvatar(
                                                    userType = userType,
                                                    avatarID = index
                                                )
//                                                when (userType) {
//                                                    Constants.REGULAR_USER -> navController.navigate(
//                                                        Screen.RegularMainScreen.route
//                                                    )
//                                                    Constants.SUPERVISOR_USER -> navController.navigate(
//                                                        Screen.SupervisorMainScreen.route
//                                                    )
//                                                }
                                                myCallback(index)
                                                setAvatarDialog(avatarDialogState = false)
                                            })
                                }
                            }
                        }
                    }

//                    AvatarSelector(userType = userType, navController = navController)
                }
            }

        }
    }
}
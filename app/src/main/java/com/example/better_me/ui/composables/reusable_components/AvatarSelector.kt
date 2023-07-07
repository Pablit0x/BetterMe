package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.better_me.R
import com.example.better_me.data.model.Constants.REGULAR_USER
import com.example.better_me.data.model.Constants.SUPERVISOR_USER
import com.example.better_me.data.screens.Screen
import com.example.better_me.ui.composables.screens.select_avatar.AvatarViewModel
import com.example.better_me.ui.composables.screens.supervisor.main.setAvatarDialog
import com.example.better_me.ui.theme.White

private lateinit var selectAvatarViewModel: AvatarViewModel

val avatarList: List<Int> = listOf(
    R.drawable.ic_avatar1,
    R.drawable.ic_avatar2,
    R.drawable.ic_avatar3,
    R.drawable.ic_avatar4,
    R.drawable.ic_avatar5,
    R.drawable.ic_avatar6,
    R.drawable.ic_avatar7,
    R.drawable.ic_avatar8,
)

/**
 * The composable function displays the choice of avatar icons.
 * @param userType A user type specifier is used to navigate to the correct screen after the avatar change
 * @param navController The navigation controller used to handle navigation to different screens
 */
@Composable
fun AvatarSelector(userType: Int, navController: NavHostController, myCallback: (Int) -> Unit) {
    selectAvatarViewModel = AvatarViewModel()
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
                                when (userType) {
                                    REGULAR_USER -> navController.navigate(
                                        Screen.RegularMainScreen.route
                                    )
                                    SUPERVISOR_USER -> navController.navigate(
                                        Screen.SupervisorMainScreen.route
                                    )
                                }
                                setAvatarDialog(avatarDialogState = false)
                            })
                }
            }
        }
    }
}
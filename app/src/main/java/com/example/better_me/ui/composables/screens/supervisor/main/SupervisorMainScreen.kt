package com.example.better_me.ui.composables.screens.supervisor.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.better_me.R
import com.example.better_me.data.model.Constants
import com.example.better_me.data.model.Constants.SUPERVISOR_USER
import com.example.better_me.data.model.RegularUser
import com.example.better_me.ui.composables.reusable_components.AnimatedText
import com.example.better_me.ui.composables.reusable_components.SupervisorUserBottomBar
import com.example.better_me.ui.composables.reusable_components.SupervisorUserTopBar
import com.example.better_me.ui.composables.reusable_components.UserItem
import com.example.better_me.ui.composables.screens.select_avatar.AvatarViewModel
import com.example.better_me.ui.composables.screens.select_avatar.ShowSelectAvatarDialog
import com.example.better_me.ui.theme.White
import com.example.better_me.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth

private lateinit var supervisorMainViewModel: SupervisorMainViewModel
private lateinit var firebaseAuth: FirebaseAuth
var isSelectAvatarDialogOpen: MutableState<Boolean> = mutableStateOf(false)


@OptIn(ExperimentalMaterialApi::class)
/**
 * This composable function contains the UI that represents the main/home supervisor's screen which is a list of supervised by them users
 * @param navController Navigation controller to navigate between screens
 */

@Composable
fun SupervisorMainScreen(navController: NavHostController) {
    val avatarViewModel = AvatarViewModel()
    var avatar by remember { mutableStateOf(0) }
    var users: MutableList<RegularUser?> by remember { mutableStateOf(mutableListOf()) }

    firebaseAuth = FirebaseAuth.getInstance()
    if (firebaseAuth.currentUser != null) {
        supervisorMainViewModel = SupervisorMainViewModel()
        SideEffect {
            avatarViewModel.getAvatarID(
                userID = firebaseAuth.currentUser!!.uid,
                userType = Constants.SUPERVISOR_USER
            ) {
                avatar = it
            }
            supervisorMainViewModel.getSupervisedUsers { supervisedUsers ->
                if (!supervisedUsers.isNullOrEmpty()) {
                    users = supervisedUsers
                    users.sortBy { it!!.username }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SupervisorUserTopBar(
                navController = navController,
                title = "Supervised Users",
                isHome = true,
                avatarID = avatar
            )
        },
        bottomBar = {
            SupervisorUserBottomBar(
                navController = navController
            )
        }, content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (users.isNullOrEmpty()) {
                    AnimatedText(text = stringResource(id = R.string.no_supervised_users))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.9f)
                    ) {
                        itemsIndexed(items = users,
                            key = { _, item ->
                                item.hashCode()
                            }
                        ) { _, item ->
                            val state = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        users.remove(item)
                                        supervisorMainViewModel.deleteSupervisedUser(
                                            userID = item!!.uid!!
                                        )
                                    }
                                    true
                                }
                            )
                            SwipeToDismiss(
                                dismissThresholds = { FractionalThreshold(0.7f) },
                                state = state,
                                background = {
                                    val color = when (state.dismissDirection) {
                                        DismissDirection.StartToEnd -> Color.Transparent
                                        DismissDirection.EndToStart -> primaryColor
                                        null -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = color)
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "delete icon",
                                            tint = White,
                                            modifier = Modifier.align(Alignment.CenterEnd)
                                        )
                                    }
                                },
                                dismissContent = {
                                    if (item != null) {
                                        UserItem(item, navController = navController)
                                    }
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }
                    }
                }
            }
            ShowSelectAvatarDialog(
                isDialogOpen = isSelectAvatarDialogOpen,
                userType = SUPERVISOR_USER,
                navController = navController
            ) { avatarID ->
                avatar = avatarID
            }
        })
}

/**
 * This function changes the isSelectAvatarDialogOpen state
 * @param avatarDialogState Desired boolean value
 */
fun setAvatarDialog(avatarDialogState: Boolean) {
    isSelectAvatarDialogOpen.value = avatarDialogState
}

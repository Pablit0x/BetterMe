package com.example.better_me.ui.composables.screens.regular.user_supervisors

import android.annotation.SuppressLint
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
import com.example.better_me.data.model.SupervisorUser
import com.example.better_me.ui.composables.reusable_components.AnimatedText
import com.example.better_me.ui.composables.reusable_components.RegularUserBottomBar
import com.example.better_me.ui.composables.reusable_components.RegularUserTopBar
import com.example.better_me.ui.composables.reusable_components.SupervisorUserItem
import com.example.better_me.ui.composables.screens.regular.add_supervisor.ShowAddSupervisorDialog
import com.example.better_me.ui.theme.White
import com.example.better_me.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth

var isAddSupervisorDialogOpen: MutableState<Boolean> = mutableStateOf(false)
private lateinit var regularSupervisorsViewModel: RegularSupervisorsViewModel

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterialApi::class)
/**
 * This composable function contains the UI that represents the main/home supervisor's screen which is a list of supervised by them users
 * @param navController Navigation controller to navigate between screens
 */

@Composable
fun UserSupervisorsScreen(navController: NavHostController) {
    val firebaseAuth = FirebaseAuth.getInstance()
    regularSupervisorsViewModel = RegularSupervisorsViewModel()
    var users: MutableList<SupervisorUser> by remember { mutableStateOf(mutableListOf()) }

    if (firebaseAuth.currentUser != null) {
        SideEffect {
            regularSupervisorsViewModel.getUserSupervisors { supervisors ->
                if (!supervisors.isNullOrEmpty()) {
                    users = supervisors
                    users.sortBy { it.username }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            RegularUserTopBar(
                navController = navController,
                title = "My Supervisors",
                showAddIcon = true,
                isHome = false,
                2
            )
        },
        bottomBar = {
            RegularUserBottomBar(
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
                    AnimatedText(text = stringResource(id = R.string.user_not_supervised))
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
                                        regularSupervisorsViewModel.deleteSupervisor(item.uid!!)
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
                                    SupervisorUserItem(item)
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }
                    }
                }
            }
        })
    ShowAddSupervisorDialog(
        isDialogOpen = isAddSupervisorDialogOpen
    )
}

/**
 * This function changes the isAddSupervisorDialogOpen state
 * @param showDialog Desired boolean value
 */
fun setSupervisorDialog(showDialog: Boolean) {
    isAddSupervisorDialogOpen.value = showDialog
}
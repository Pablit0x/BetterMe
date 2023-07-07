package com.example.better_me.ui.composables.screens.supervisor.user_activities

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.better_me.R
import com.example.better_me.data.model.Activity
import com.example.better_me.data.model.Constants.SUPERVISOR_USER
import com.example.better_me.ui.composables.reusable_components.ActivityItem
import com.example.better_me.ui.composables.reusable_components.AddFeedbackDialog
import com.example.better_me.ui.composables.reusable_components.SupervisorUserBottomBar
import com.example.better_me.ui.composables.reusable_components.SupervisorUserTopBar
import com.example.better_me.ui.composables.screens.regular.add_activity.ShowAddActivityAlertDialog
import com.example.better_me.ui.theme.White
import com.example.better_me.ui.theme.Yellow
import com.example.better_me.ui.theme.secondaryColor
import java.time.LocalDateTime


var isAddActivityDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isAddFeedbackDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var feedbackComment: MutableState<String> = mutableStateOf("")
var date: String = ""
lateinit var supervisorViewViewModel: SupervisorViewViewModel

@SuppressLint("MutableCollectionMutableState")
@OptIn(
    ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
/**
 * This composable function displays the screen with records of the selected user for the specified date
 * @param navController Navigation controller used to navigate between screens
 * @param selectedDate The date for which the records will be displayed
 * @param userUID The ID of the user for which the records will be displayed
 * @param username The username of the user for which the records will be displayed
 */
@Composable
fun SupervisorViewScreen(
    navController: NavHostController,
    selectedDate: String,
    userUID: String,
    username: String
) {
    supervisorViewViewModel = SupervisorViewViewModel()
    var userRating by remember { mutableStateOf(0) }
    var activities by remember { mutableStateOf(mutableListOf<Activity>()) }

    SideEffect {
        val currentDate = LocalDateTime.now()
        val formattedDate =
            "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        date = selectedDate.ifEmpty {
            formattedDate
        }
        supervisorViewViewModel.getPublicActivities(date, uid = userUID) {
            if (!it.isNullOrEmpty()) {
                activities = it
            }
        }
        supervisorViewViewModel.getRating(date, uid = userUID) {
            userRating = it
        }
        supervisorViewViewModel.getFeedback(date = date, uid = userUID) {
            feedbackComment = mutableStateOf(it)
        }
    }
    Scaffold(
        bottomBar = {
            SupervisorUserBottomBar(navController = navController)
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {

                FloatingActionButton(
                    onClick = { isAddActivityDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = White,
                ) {
                    Icon(imageVector = Icons.Default.Add, "add activity icon")
                }

                Spacer(modifier = Modifier.padding(bottom = 10.dp))

                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = stringResource(id = R.string.leave_feedback),
                            fontFamily = FontFamily.Monospace
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_feedback),
                            contentDescription = "add feedback icon"
                        )
                    },
                    onClick = { isAddFeedbackDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = White,
                )
            }

        },
        topBar = {
            SupervisorUserTopBar(
                navController = navController,
                title = "$username Activities",
                isHome = false,
                avatarID = 0
            )
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(padding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .background(color = Color(0xFF2D4263))
                ) {
                    val dateArr = date.split("-")
                    Text(
                        text = "${dateArr[0]} ${supervisorViewViewModel.getMonth(dateArr[1].toInt())} ${dateArr[2]}",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (userRating != 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            for (i in 1..userRating) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_rating_star),
                                    contentDescription = "star icon",
                                    modifier = Modifier
                                        .width(52.dp)
                                        .height(52.dp),
                                    tint = Yellow
                                )
                            }
                        }
                    }
                }
                if (activities.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.9f)
                    ) {
                        itemsIndexed(items = activities,
                            key = { _, item ->
                                item.hashCode()
                            }
                        ) { _, item ->
                            ActivityItem(activity = item, date = date, userType = SUPERVISOR_USER)
                        }
                    }
                }

                ShowAddActivityAlertDialog(
                    isDialogOpen = isAddActivityDialogOpen,
                    date = date,
                    userType = SUPERVISOR_USER,
                    userID = userUID
                ) { activity ->
                    if (!activities.contains(activity)) {
                        activities.add(activity)
                    }
                }
                AddFeedbackDialog(
                    isDialogOpen = isAddFeedbackDialogOpen,
                    date = date,
                    userID = userUID,
                    feedbackComment = feedbackComment
                )
            }
        })
}


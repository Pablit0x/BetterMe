package com.example.better_me.ui.composables.screens.supervisor.calendar

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.better_me.data.model.Constants.SUPERVISOR_USER
import com.example.better_me.ui.composables.reusable_components.DatePickerView
import com.example.better_me.ui.composables.reusable_components.SupervisorUserBottomBar
import com.example.better_me.ui.composables.reusable_components.SupervisorUserTopBar

/**
 * This composable function represents the supervisor user calendar view screen
 * @param userUID ID of the user of which the calendar the supervisor is accessing
 * @param username Username of the user of which the calendar the supervisor is accessing
 * @param navController Navigation controller used to navigate between screens
 */
@Composable
fun SupervisorCalendarScreen(navController: NavHostController, username: String, userUID: String) {
    Scaffold(bottomBar = { SupervisorUserBottomBar(navController = navController) }, topBar = {
        SupervisorUserTopBar(
            navController = navController,
            title = "$username Calendar",
            isHome = false,
            avatarID = 0
        )
    }, content = {
        DatePickerView(
            navController = navController,
            userType = SUPERVISOR_USER,
            userUID = userUID,
            username = username
        )
    })
}
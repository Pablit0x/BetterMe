package com.example.better_me.data.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class SupervisorBottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Requests : SupervisorBottomBarScreen(
        route = "request",
        title = "Request",
        icon = Icons.Default.Notifications
    )

    object Home : SupervisorBottomBarScreen(
        route = "home/supervisor",
        title = "Home",
        icon = Icons.Default.Home
    )

//    object Messenger : SupervisorBottomBarScreen(
//        route = "messenger",
//        title = "Messenger",
//        icon = Icons.Default.Email
//    )
}

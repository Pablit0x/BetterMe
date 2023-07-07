package com.example.better_me.data.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class UserBottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Calendar : UserBottomBarScreen(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.DateRange
    )

    object Home : UserBottomBarScreen(
        route = "home/user",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Supervisors : UserBottomBarScreen(
        route = "supervisors",
        title = "Supervisors",
        icon = Icons.Default.Person
    )
}

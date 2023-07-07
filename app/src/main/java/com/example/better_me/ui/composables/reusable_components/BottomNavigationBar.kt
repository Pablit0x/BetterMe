package com.example.better_me.ui.composables.reusable_components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.better_me.data.screens.SupervisorBottomBarScreen
import com.example.better_me.data.screens.UserBottomBarScreen

/**
 * This composable function displays the bottom navigation bar designed for the Regular Users
 * @param navController The navigation controller used to handle bottom bar navigation to different screens
 */
@Composable
fun RegularUserBottomBar(navController: NavHostController) {
    val screens = listOf(
        UserBottomBarScreen.Calendar,
        UserBottomBarScreen.Home,
        UserBottomBarScreen.Supervisors
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddUserItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }
}

/**
 * This function handles the Regular User bottom bar navigation and specifies how each navigation item will display.
 * @param screen Screen object that will be added to the navigation bar
 * @param currentDestination Current navigation destination
 * @param navController Navigation controller used to navigate between bottom bar screens
 */
@Composable
fun RowScope.AddUserItem(
    screen: UserBottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "bottom navigation Icon") },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}

/**
 * This composable function displays the bottom navigation bar designed for the Supervisor Users
 * @param navController The navigation controller used to handle bottom bar navigation to different screens
 */
@Composable
fun SupervisorUserBottomBar(navController: NavHostController) {
    val screens = listOf(
        SupervisorBottomBarScreen.Requests,
        SupervisorBottomBarScreen.Home
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddSupervisorItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }
}

/**
 * This function handles the Supervisor User bottom bar navigation and specifies how each navigation item will display.
 * @param screen Screen object that will be added to the navigation bar
 * @param currentDestination Current navigation destination
 * @param navController navigation controller used to navigate between bottom bar screens
 */
@Composable
fun RowScope.AddSupervisorItem(
    screen: SupervisorBottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "bottom navigation Icon") },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}
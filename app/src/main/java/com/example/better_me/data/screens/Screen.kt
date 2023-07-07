package com.example.better_me.data.screens

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
    object SignUpScreen : Screen(route = "sign_up_screen")
    object ForgotPasswordScreen : Screen(route = "forgot_password_screen")
    object RegularMainScreen : Screen(route = "regular_main_screen?date={date}") {
        fun passDate(date: String): String {
            return "regular_main_screen?date=$date"
        }
    }

    object SupervisorMainScreen : Screen(route = "supervisor_main_screen")
    object SupervisorCalendarScreen :
        Screen(route = "supervisor_calendar_screen?username={username}&uid={uid}") {
        fun passUsernameAndUID(username: String, uid: String): String {
            return "supervisor_calendar_screen?username=$username&uid=$uid"
        }
    }

    object SupervisorViewScreen :
        Screen(route = "supervisor_view_screen?username={username}&uid={uid}&date={date}") {
        fun passUsernameDateAndUID(username: String, uid: String, date: String): String {
            return "supervisor_view_screen?username=$username&uid=$uid&date=$date"
        }
    }
}

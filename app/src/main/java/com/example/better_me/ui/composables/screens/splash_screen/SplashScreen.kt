package com.example.better_me.ui.composables.screens.splash_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.better_me.R
import com.example.better_me.ui.theme.NavyBlue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

private lateinit var splashScreenViewModel: SplashScreenViewModel

/**
 * This composable function displays the animated splash screen for 4 seconds and determines if the user is logged in
 * If the user is logged in, they will be delegated to the appropriate screen depending on if the user is regular or a supervisor
 * @param navController Navigation controller to, later on, navigate to the appropriate screen
 */

@Composable
fun AnimatedSplashScreen(navController: NavController) {

    splashScreenViewModel = SplashScreenViewModel()
    val firebaseAuth = FirebaseAuth.getInstance()

    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        splashScreenViewModel.checkLoginStatus(
            firebaseAuth = firebaseAuth,
            navController = navController
        )
    }
    SplashScreen(alpha = alphaAnim.value)
}

/**
 * This composable contains splash screen image
 */
@Composable
fun SplashScreen(alpha: Float) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .background(NavyBlue)
            .fillMaxSize()
    ) {
        Image(
            painter = key(R.drawable.splash_screen) { painterResource(R.drawable.splash_screen) },
            contentDescription = null,
            modifier = Modifier
                .size(450.dp)
                .alpha(alpha = alpha),
        )
    }
}
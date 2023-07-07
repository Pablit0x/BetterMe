package com.example.better_me.ui.composables.reusable_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.better_me.ui.composables.screens.supervisor.main.setAvatarDialog
import kotlinx.coroutines.delay

/**
 * The composable function displays an image with an animation that makes it slowly appear.
 * @param imageResource Image resource to the desired image
 */
@Composable
fun AnimatedAvatarImage(imageResource: Int) {
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
    }

    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "",
        modifier = Modifier
            .fillMaxHeight()
            .size(45.dp)
            .clickable {
                setAvatarDialog(true)
            }
            .alpha(alpha = alphaAnim.value)
    )
}
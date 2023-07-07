package com.example.better_me.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val myColorPalette = darkColors(
    primary = Color(0xFF2D4263),
    primaryVariant = Color(0xFFC84B31),
    secondary = Color(0xFFECDBBA),
    background = Color(0xFF2D4263),
    surface = Color(0xFF191919),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun BetterMe_Theme(
    content: @Composable () -> Unit
) {
    val colors = myColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
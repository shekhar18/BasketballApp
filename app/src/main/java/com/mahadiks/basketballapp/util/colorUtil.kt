package com.mahadiks.basketballapp.util

import androidx.compose.ui.graphics.Color

fun lightenColor(color: Color, factor: Float = 0.2f): Color {
    val r = color.red + (1 - color.red) * factor
    val g = color.green + (1 - color.green) * factor
    val b = color.blue + (1 - color.blue) * factor

    return Color(r, g, b)
}
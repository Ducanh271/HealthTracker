package com.example.healthtracker.ui.theme

import android.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun accentColorArgb(themeName: String): Int {
    val theme = try {
        AppThemeType.valueOf(themeName)
    } catch (e: IllegalArgumentException) {
        AppThemeType.DEFAULT
    }

    val hsv = FloatArray(3)
    Color.colorToHSV(theme.color.toArgb(), hsv)
    hsv[1] = hsv[1].coerceIn(MIN_SATURATION, MAX_SATURATION)
    hsv[2] = hsv[2].coerceAtLeast(MIN_BRIGHTNESS)
    return Color.HSVToColor(hsv)
}

private const val MIN_SATURATION = 0.55f
private const val MAX_SATURATION = 0.9f
private const val MIN_BRIGHTNESS = 0.8f

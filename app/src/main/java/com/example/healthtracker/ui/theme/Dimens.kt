package com.example.healthtracker.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val marginMobile: Dp = 16.dp,

    // Độ bo góc (Corners)
    val cornerSmall: Dp = 4.dp,
    val cornerMedium: Dp = 12.dp,
    val cornerLarge: Dp = 16.dp,
    val cornerExtraLarge: Dp = 24.dp,
    val cornerFull: Dp = 9999.dp,

    val buttonHeight: Dp = 56.dp,
    val borderWidth: Dp = 1.dp,
    val strokeWidth: Dp = 2.dp,
    val iconSmall: Dp = 18.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 28.dp,

    val avatarSize: Dp = 128.dp,
    val iconEditBadge: Dp = 20.dp,
    val bentoCardAspect: Float = 1.2f,
    val spacingXS: Dp = 2.dp,
    val inputMinWidth: Dp = 40.dp,

    // Dashboard
    val progressCircleSize: Dp = 250.dp,
    val strokeThick: Dp = 12.dp,
    val chartBarHeight: Dp = 160.dp,
    val chartLineHeight: Dp = 120.dp,
    val chartLineStroke: Dp = 3.dp,
    val chartBarCorner: Dp = 8.dp,
    val chartBarWidthFraction: Float = 0.6f,
    val chartBarMinHeightFraction: Float = 0.05f,
    val fabClearance: Dp = 100.dp
)

val LocalDimens = compositionLocalOf { Dimensions() }
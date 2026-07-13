package com.example.healthtracker.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val base: Dp = 4.dp,
    val sm: Dp = 8.dp, // small, medium, large, extra large
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val marginMobile: Dp = 16.dp,
    val cornerSmall: Dp = 4.dp,
    val cornerMedium: Dp = 12.dp,
    val cornerLarge: Dp = 16.dp,
    val cornerExtraLarge: Dp = 24.dp,
    val cornerFull: Dp = 9999.dp
)

val LocalDimens = compositionLocalOf { Dimensions() }
// định nghĩa LocalDimens là biến toàn cục, nên các composable gọi thoải mái
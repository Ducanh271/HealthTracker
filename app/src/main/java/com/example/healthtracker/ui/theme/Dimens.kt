package com.example.healthtracker.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    // Kích thước khoảng cách chung
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

    // CÁC KÍCH THƯỚC ĐẶC THÙ (MỚI THÊM VÀO)
    val buttonHeight: Dp = 56.dp,    // Chiều cao tiêu chuẩn của nút bấm lớn
    val borderWidth: Dp = 1.dp,      // Độ dày tiêu chuẩn của viền
    val strokeWidth: Dp = 2.dp,      // Độ dày nét vẽ (dùng cho Progress Bar)
    val iconSmall: Dp = 18.dp,       // Icon cỡ nhỏ (info)
    val iconMedium: Dp = 24.dp       // Icon cỡ vừa (icon chuẩn Material)
)

val LocalDimens = compositionLocalOf { Dimensions() }
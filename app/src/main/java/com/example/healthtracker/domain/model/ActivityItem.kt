package com.example.healthtracker.domain.model

import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Pool

// Bạn có thể đặt file này ở domain/model/ActivityItem.kt
data class ActivityItem(
    val id: Int,
    val name: String,
    val duration: Int, // Phút
    val time: String, // VD: "08:30 Sáng"
    val caloriesBurned: Int,
    val type: ActivityType // Để set màu sắc/icon
)

enum class ActivityType {
    STRENGTH, CARDIO, CYCLING, SWIMMING, WALKING
}

data class ActivitySuggestion(
    val name: String,
    val mets: Float,
    val type: ActivityType
)
// Ví dụ Model dùng cho danh mục tìm kiếm
data class ActivityCatalogItem(
    val id: Int,
    val name: String,
    val description: String,
    val metValue: Float,
    val category: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
fun ActivityItem.toCatalogItem(): ActivityCatalogItem {
    // Lưu ý: Icon là một ImageVector, bạn cần map ActivityType sang Icon tương ứng
    val icon = when (this.type) {
        ActivityType.STRENGTH -> androidx.compose.material.icons.Icons.Default.FitnessCenter
        ActivityType.CARDIO -> androidx.compose.material.icons.Icons.Default.DirectionsRun
        ActivityType.CYCLING -> androidx.compose.material.icons.Icons.Default.PedalBike
        ActivityType.SWIMMING -> androidx.compose.material.icons.Icons.Default.Pool
        ActivityType.WALKING -> androidx.compose.material.icons.Icons.Default.DirectionsWalk
    }

    return ActivityCatalogItem(
        id = this.id, // Lưu ý: Nếu id của log khác với id catalog, bạn cần lưu lại activityId trong ActivityItem
        name = this.name,
        description = "Chỉnh sửa hoạt động", // Hoặc mô tả tùy ý
        metValue = 0f, // Tạm thời để 0, vì log đã lưu calories rồi
        category = this.type.name,
        icon = icon
    )
}
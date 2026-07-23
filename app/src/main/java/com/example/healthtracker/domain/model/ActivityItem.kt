package com.example.healthtracker.domain.model

import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Pool

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
data class ActivityCatalogItem(
    val id: Int,
    val name: String,
    val description: String,
    val metValue: Float,
    val category: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
fun ActivityItem.toCatalogItem(): ActivityCatalogItem {
    val icon = when (this.type) {
        ActivityType.STRENGTH -> androidx.compose.material.icons.Icons.Default.FitnessCenter
        ActivityType.CARDIO -> androidx.compose.material.icons.Icons.Default.DirectionsRun
        ActivityType.CYCLING -> androidx.compose.material.icons.Icons.Default.PedalBike
        ActivityType.SWIMMING -> androidx.compose.material.icons.Icons.Default.Pool
        ActivityType.WALKING -> androidx.compose.material.icons.Icons.Default.DirectionsWalk
    }

    return ActivityCatalogItem(
        id = this.id,
        name = this.name,
        description = "Chỉnh sửa hoạt động",
        metValue = 0f,
        category = this.type.name,
        icon = icon
    )
}
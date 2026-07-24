package com.example.healthtracker.domain.usecase.activity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SelfImprovement
import com.example.healthtracker.domain.model.ActivityCatalogEntry
import com.example.healthtracker.domain.model.ActivityCatalogItem

internal fun ActivityCatalogEntry.toCatalogItem(): ActivityCatalogItem {
    val (category, icon) = when {
        name.contains("Chạy") || name.contains("Đi bộ") -> "Cardio" to Icons.Default.DirectionsRun
        name.contains("Đạp xe") -> "Cardio" to Icons.Default.PedalBike
        name.contains("Bơi") -> "Thể thao" to Icons.Default.Pool
        name.contains("tạ") || name.contains("Gym") -> "Sức mạnh" to Icons.Default.FitnessCenter
        name.contains("Yoga") -> "Yoga" to Icons.Default.SelfImprovement
        else -> "Khác" to Icons.Default.DirectionsRun
    }

    return ActivityCatalogItem(
        id = id,
        name = name,
        description = category,
        metValue = metValue,
        category = category,
        icon = icon
    )
}

package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.model.ActivityCatalogEntry
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.model.ActivityCategory

internal fun ActivityCatalogEntry.toCatalogItem(): ActivityCatalogItem = ActivityCatalogItem(
    id = id,
    name = name,
    metValue = metValue,
    category = categoryOf(name)
)

private fun categoryOf(name: String): ActivityCategory = when {
    name.contains("Đạp xe") -> ActivityCategory.CYCLING
    name.contains("Chạy") || name.contains("Đi bộ") -> ActivityCategory.RUNNING
    name.contains("Bơi") -> ActivityCategory.SWIMMING
    name.contains("tạ") || name.contains("Gym") -> ActivityCategory.STRENGTH
    name.contains("Yoga") -> ActivityCategory.YOGA
    else -> ActivityCategory.OTHER
}

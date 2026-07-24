package com.example.healthtracker.domain.model

data class ActivityItem(
    val id: Int,
    val name: String,
    val duration: Int,
    val time: String,
    val caloriesBurned: Int,
    val type: ActivityType
)

enum class ActivityType {
    STRENGTH, CARDIO, CYCLING, SWIMMING, WALKING
}

enum class ActivityCategory {
    RUNNING, CYCLING, SWIMMING, STRENGTH, YOGA, OTHER
}

data class ActivityCatalogItem(
    val id: Int,
    val name: String,
    val metValue: Float,
    val category: ActivityCategory
)

fun ActivityItem.toCatalogItem(): ActivityCatalogItem = ActivityCatalogItem(
    id = id,
    name = name,
    metValue = 0f,
    category = type.toCategory()
)

private fun ActivityType.toCategory(): ActivityCategory = when (this) {
    ActivityType.STRENGTH -> ActivityCategory.STRENGTH
    ActivityType.CARDIO -> ActivityCategory.RUNNING
    ActivityType.CYCLING -> ActivityCategory.CYCLING
    ActivityType.SWIMMING -> ActivityCategory.SWIMMING
    ActivityType.WALKING -> ActivityCategory.RUNNING
}

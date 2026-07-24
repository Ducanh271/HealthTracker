package com.example.healthtracker.ui.features.diary.activity

import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.model.ActivityItem
import java.time.LocalDate

data class ActivityDiaryState(
    val selectedDate: LocalDate = LocalDate.now(),
    val totalBurnedCalories: Int = 0,
    val targetBurnCalories: Int = 500,
    val activities: List<ActivityItem> = emptyList(),
    val userWeight: Float = 70f
)

data class ActivityLogTarget(
    val catalogItem: ActivityCatalogItem,
    val initialDuration: Int = DEFAULT_DURATION_MINUTES,
    val existingLogId: Int? = null
) {
    companion object {
        const val DEFAULT_DURATION_MINUTES = 30
    }
}

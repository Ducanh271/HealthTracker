package com.example.healthtracker.ui.features.diary.activity

import com.example.healthtracker.domain.model.ActivityItem
import java.time.LocalDate

data class ActivityDiaryState(
    val selectedDate: LocalDate = LocalDate.now(),
    val totalBurnedCalories: Int = 0,
    val targetBurnCalories: Int = 500,
    val activities: List<ActivityItem> = emptyList(),
    val userWeight: Float = 70f
)
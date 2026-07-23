package com.example.healthtracker.domain.model

data class ActivityLog(
    val id: Int = 0,
    val date: String,
    val activityName: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)

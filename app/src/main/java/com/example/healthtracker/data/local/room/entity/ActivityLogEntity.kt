package com.example.healthtracker.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val activityName: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)

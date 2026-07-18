package com.example.healthtracker.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String ,
    val activityName: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)

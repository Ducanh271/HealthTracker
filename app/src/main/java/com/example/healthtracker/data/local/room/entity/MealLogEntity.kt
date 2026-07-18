package com.example.healthtracker.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_logs")
data class MealLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val mealType: String,
    val foodName: String,
    val servingCount: Int,
    val totalCalories: Int
)

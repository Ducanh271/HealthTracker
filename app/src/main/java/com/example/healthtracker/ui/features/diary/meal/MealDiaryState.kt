package com.example.healthtracker.ui.features.diary.meal

import com.example.healthtracker.domain.model.Meal
import java.time.LocalDate

data class MealDiaryState(
    val selectedDate: LocalDate = LocalDate.now(),
    val targetCalories: Int = 2000,
    val totalCalories: Int = 0,
    val breakfast: List<Meal> = emptyList(),
    val lunch: List<Meal> = emptyList(),
    val dinner: List<Meal> = emptyList(),
    val snacks: List<Meal> = emptyList()
)
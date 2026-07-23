package com.example.healthtracker.ui.features.diary.meal

import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.model.UserProfile
import java.time.LocalDate

data class MealDiaryState(
    val selectedDate: LocalDate = LocalDate.now(),
    val targetCalories: Int = UserProfile.DEFAULT_TDEE,
    val totalCalories: Int = 0,
    val breakfast: List<Meal> = emptyList(),
    val lunch: List<Meal> = emptyList(),
    val dinner: List<Meal> = emptyList(),
    val snacks: List<Meal> = emptyList()
)
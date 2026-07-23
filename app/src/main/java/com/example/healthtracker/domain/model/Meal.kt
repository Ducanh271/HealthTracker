package com.example.healthtracker.domain.model

data class Meal(
    val id: Int = 0,
    val date: String,
    val mealType: MealType,
    val foodName: String,
    val servingCount: Int,
    val totalCalories: Int
)
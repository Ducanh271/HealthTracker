package com.example.healthtracker.domain.model

data class Meal(
    val id: Int = 0,
    val date: String,
    val mealType: String,
    val foodName: String,
    val servingCount: Int,
    val totalCalories: Int
) {
    val amountInfo: String
        get() = "$servingCount phần"
}
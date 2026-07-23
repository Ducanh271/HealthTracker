package com.example.healthtracker.domain.model

data class FoodItem(
    val id: Int = 0,
    val name: String,
    val servingSize: String,
    val calories: Int
)
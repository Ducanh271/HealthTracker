package com.example.healthtracker.domain.model

data class FoodItem(
    val id: Int,
    val name: String,
    val servingSize: String,
    val calories: Int
)
package com.example.healthtracker.domain.model

data class UserProfile(
    val name: String,
    val age: Int,
    val dob: String,
    val gender: String,
    val weight: Float,
    val height: Float,
    val activityLevel: Int,
    val goal: String,
    val tdee: Int
)
package com.example.healthtracker.domain.model

data class UserProfile(
    val name: String,
    val age: Int,
    val dob: String,
    val gender: Gender,
    val weight: Float,
    val height: Float,
    val activityLevel: Int,
    val goal: Goal,
    val tdee: Int
) {
    companion object {
        const val DEFAULT_TDEE = 2000
    }
}
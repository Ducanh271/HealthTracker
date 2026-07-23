package com.example.healthtracker.data.local.model

import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.UserProfile

data class UserEntity(
    val name: String,
    val age: Int,
    val dob: String,
    val gender: String,
    val weight: Float,
    val height: Float,
    val activityLevel: Int,
    val goal: String,
    val tdee: Int
) {
    fun toDomainModel(): UserProfile {
        return UserProfile(
            name = name,
            age = age,
            dob = dob,
            gender = Gender.entries.find { it.name == gender } ?: Gender.MALE,
            weight = weight,
            height = height,
            activityLevel = activityLevel,
            goal = Goal.entries.find { it.name == goal } ?: Goal.MAINTAIN_WEIGHT,
            tdee = tdee
        )
    }
}
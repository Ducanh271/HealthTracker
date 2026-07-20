package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userProfile: Flow<UserProfile>
    suspend fun saveUserProfile(
        name: String, age: Int, gender: String,
        weight: Float, height: Float, activityLevel: Int,
        goal: String, tdee: Int
    )
    suspend fun saveOnboardingStatus(completed: Boolean)
}
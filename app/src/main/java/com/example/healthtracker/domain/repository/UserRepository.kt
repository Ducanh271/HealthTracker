package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userProfile: Flow<UserProfile>
    val appTheme: Flow<String>
    val appLanguage: Flow<String>
    val fontSize: Flow<String>
    val isOnboardingCompleted: Flow<Boolean>
    val notificationsEnabled: Flow<Boolean>
    suspend fun saveUserProfile(
        name: String, age: Int, dob: String, gender: Gender,
        weight: Float, height: Float, activityLevel: Int,
        goal: Goal, tdee: Int
    )
    suspend fun saveOnboardingStatus(completed: Boolean)
    suspend fun saveAppTheme(theme: String)
    suspend fun saveAppLanguage(lang: String)
    suspend fun saveFontSize(size: String)
    suspend fun saveNotificationsEnabled(enabled: Boolean)
    val appMode: Flow<String>
    suspend fun saveAppMode(mode: String)
}
package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.datastore.SettingsDataStore
import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : UserRepository {
    override val userProfile = settingsDataStore.userProfile

    override suspend fun saveUserProfile(name: String, age: Int, gender: String,
                                         weight: Float, height: Float, activityLevel: Int,
                                         goal: String, tdee: Int) { // Các tham số y hệt
        settingsDataStore.saveUserProfile(name, age, gender, weight, height, activityLevel, goal, tdee)
    }

    override suspend fun saveOnboardingStatus(completed: Boolean) {
        settingsDataStore.saveOnboardingStatus(completed)
    }
}
package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.datastore.SettingsDataStore
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : UserRepository {
    override val userProfile = settingsDataStore.userProfile
    override val appTheme: Flow<String> = settingsDataStore.appTheme
    override val appLanguage: Flow<String> = settingsDataStore.appLanguage
    override val fontSize: Flow<String> = settingsDataStore.fontSize
    override suspend fun saveUserProfile(name: String, age: Int, gender: String,
                                         weight: Float, height: Float, activityLevel: Int,
                                         goal: String, tdee: Int) {
        settingsDataStore.saveUserProfile(name, age, gender, weight, height, activityLevel, goal, tdee)
    }

    override suspend fun saveOnboardingStatus(completed: Boolean) {
        settingsDataStore.saveOnboardingStatus(completed)
    }
    override suspend fun saveAppTheme(theme: String) {
        settingsDataStore.saveAppTheme(theme)
    }

    override suspend fun saveAppLanguage(lang: String) {
        settingsDataStore.saveAppLanguage(lang)
    }

    override suspend fun saveFontSize(size: String) {
        settingsDataStore.saveFontSize(size)
    }
    override val appMode: Flow<String> = settingsDataStore.appMode
    override suspend fun saveAppMode(mode: String) {
        settingsDataStore.saveAppMode(mode)
    }
}
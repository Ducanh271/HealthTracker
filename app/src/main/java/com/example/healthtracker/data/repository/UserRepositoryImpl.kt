package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.datastore.SettingsDataStore
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : UserRepository {

    override val userProfile: Flow<UserProfile> = settingsDataStore.userEntity.map { entity ->
        entity.toDomainModel()
    }

    override val appTheme: Flow<String> = settingsDataStore.appTheme
    override val appLanguage: Flow<String> = settingsDataStore.appLanguage
    override val fontSize: Flow<String> = settingsDataStore.fontSize
    override val appMode: Flow<String> = settingsDataStore.appMode
    override val isOnboardingCompleted: Flow<Boolean> = settingsDataStore.isOnboardingCompleted

    override suspend fun saveUserProfile(
        name: String, age: Int, dob: String, gender: Gender,
        weight: Float, height: Float, activityLevel: Int,
        goal: Goal, tdee: Int
    ) {
        settingsDataStore.saveUserProfile(name, age, dob, gender.name, weight, height, activityLevel, goal.name, tdee)
    }

    override suspend fun saveOnboardingStatus(completed: Boolean) { settingsDataStore.saveOnboardingStatus(completed) }
    override suspend fun saveAppTheme(theme: String) { settingsDataStore.saveAppTheme(theme) }
    override suspend fun saveAppLanguage(lang: String) { settingsDataStore.saveAppLanguage(lang) }
    override suspend fun saveFontSize(size: String) { settingsDataStore.saveFontSize(size) }
    override suspend fun saveAppMode(mode: String) { settingsDataStore.saveAppMode(mode) }
}
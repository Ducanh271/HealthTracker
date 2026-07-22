package com.example.healthtracker.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.example.healthtracker.domain.model.UserProfile

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "health_tracker_prefs")


@Singleton
class SettingsDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        val IS_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val FONT_SIZE = stringPreferencesKey("font_size")
        val APP_MODE = stringPreferencesKey("app_mode")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val USER_WEIGHT = floatPreferencesKey("user_weight")
        val USER_HEIGHT = floatPreferencesKey("user_height")
        val USER_ACTIVITY_LEVEL = intPreferencesKey("user_activity_level")
        val USER_GOAL = stringPreferencesKey("user_goal")
        val USER_TDEE = intPreferencesKey("user_tdee")

    }

    val isOnboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map { it[IS_ONBOARDING_COMPLETED] ?: false }
    val appTheme: Flow<String> = context.dataStore.data.map { it[APP_THEME] ?: "SYSTEM" }
    val appLanguage: Flow<String> = context.dataStore.data.map { it[APP_LANGUAGE] ?: "VI" }
    val fontSize: Flow<String> = context.dataStore.data.map { it[FONT_SIZE] ?: "MEDIUM" }
    val appMode: Flow<String> = context.dataStore.data.map { it[APP_MODE] ?: "SYSTEM" }
    val userProfile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            name = prefs[USER_NAME] ?: "",
            age = prefs[USER_AGE] ?: 20,
            gender = prefs[USER_GENDER] ?: "Nam",
            weight = prefs[USER_WEIGHT] ?: 0f,
            height = prefs[USER_HEIGHT] ?: 0f,
            activityLevel = prefs[USER_ACTIVITY_LEVEL] ?: 3,
            goal = prefs[USER_GOAL] ?: "maintain",
            tdee = prefs[USER_TDEE] ?: 2000
        )
    }
    suspend fun saveAppMode(mode: String) {
        context.dataStore.edit { it[APP_MODE] = mode }
    }
    suspend fun saveOnboardingStatus(completed: Boolean) {
        context.dataStore.edit { it[IS_ONBOARDING_COMPLETED] = completed }
    }


    suspend fun saveAppTheme(theme: String) {
        context.dataStore.edit { it[APP_THEME] = theme }
    }

    suspend fun saveAppLanguage(lang: String) {
        context.dataStore.edit { it[APP_LANGUAGE] = lang }
    }

    suspend fun saveFontSize(size: String) {
        context.dataStore.edit { it[FONT_SIZE] = size }
    }

    suspend fun saveUserProfile(
        name: String, age: Int, gender: String,
        weight: Float, height: Float, activityLevel: Int,
        goal: String, tdee: Int
    ) {
        context.dataStore.edit {
            it[USER_NAME] = name
            it[USER_AGE] = age
            it[USER_GENDER] = gender
            it[USER_WEIGHT] = weight
            it[USER_HEIGHT] = height
            it[USER_ACTIVITY_LEVEL] = activityLevel
            it[USER_GOAL] = goal
            it[USER_TDEE] = tdee
        }
    }
}
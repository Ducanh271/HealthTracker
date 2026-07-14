package com.example.healthtracker.data.local.datastore

import android.R
import android.content.Context
import androidx.compose.ui.graphics.vector.VNode
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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "health_tracker_prefs")
@Singleton
class SettingsDataStore @Inject constructor(
    private val context: Context
) {
    companion object{
        val IS_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val FONT_SIZE = stringPreferencesKey("font_size")

        val USER_NAME = stringPreferencesKey("user_name")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val USER_WEIGHT = floatPreferencesKey("user_weight")
        val USER_HEIGHT = floatPreferencesKey("user_height")
        val USER_ACTIVITY_LEVEL = intPreferencesKey("user_activity_level")
        val USER_GOAL = stringPreferencesKey("user_goal")
        val USER_TDEE = intPreferencesKey("user_tdee")
    }

    val isOnboardingComplicated: Flow<Boolean> = context.dataStore.data.map{it[IS_ONBOARDING_COMPLETED] ?: false}
    val appTheme: Flow<String> = context.dataStore.data.map{it[APP_THEME] ?: "SYSTEM"}
    val appLanguage: Flow<String> = context.dataStore.data.map{it[APP_LANGUAGE] ?: "VI" }
    val fontSize: Flow<String> = context.dataStore.data.map { it[FONT_SIZE] ?: "MEDIUM" }

    suspend fun saveOnboardingStatus(completed: Boolean){
        context.dataStore.edit{it[IS_ONBOARDING_COMPLETED] = completed }
    }

    suspend fun saveAppSettings(theme: String, lang: String, size: String){
        context.dataStore.edit {
            context.dataStore.edit {
                it[APP_THEME] = theme
                it[APP_LANGUAGE] = lang
                it[FONT_SIZE] = size
            }
        }
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
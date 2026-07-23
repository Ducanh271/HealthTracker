package com.example.healthtracker.domain.model

data class AppSettings(
    val userProfile: UserProfile,
    val appTheme: String,
    val appMode: String,
    val appLanguage: String,
    val fontSize: String
)

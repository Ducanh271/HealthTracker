package com.example.healthtracker.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding: Screen("onboarding_screen")
    object Dashboard: Screen("dashboard_screen")
    object Diary: Screen("diary_screen")
    object Statistics: Screen("statistics_screen")
    object Settings: Screen("settings_screen")
}


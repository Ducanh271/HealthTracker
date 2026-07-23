package com.example.healthtracker.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding: Screen("onboarding_screen")
    object Dashboard: Screen("dashboard_screen")
    object MealDiary: Screen("meal_diary_screen")
    object ActivityDiary: Screen("activity_diary_screen")
    object Settings: Screen("settings_screen")
    object EditProfile : Screen("edit_profile")
}


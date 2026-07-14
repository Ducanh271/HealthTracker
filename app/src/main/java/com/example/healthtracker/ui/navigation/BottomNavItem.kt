package com.example.healthtracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R

sealed class BottomNavItem(val route: String, val icon: ImageVector, val titleResId: Int) {
    object Dashboard : BottomNavItem(Screen.Dashboard.route, Icons.Default.Home, R.string.nav_dashboard)
    object MealDiary : BottomNavItem(Screen.MealDiary.route, Icons.Default.DateRange, R.string.nav_diary)
    object ActivityDiary : BottomNavItem(Screen.ActivityDiary.route, Icons.Default.Menu, R.string.nav_statistics)
    object Settings : BottomNavItem(Screen.Settings.route, Icons.Default.Settings, R.string.nav_settings)
}
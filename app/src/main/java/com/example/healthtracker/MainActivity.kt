package com.example.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthtracker.ui.navigation.AppNavGraph
import com.example.healthtracker.ui.navigation.Screen
import com.example.healthtracker.ui.theme.HealthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()

            HealthTrackerTheme {
                if (isOnboardingCompleted != null) {
                    val startRoute = if (isOnboardingCompleted == true) {
                        Screen.Dashboard.route
                    } else {
                        Screen.Onboarding.route
                    }
                    AppNavGraph(startDestination = startRoute)

                } else {
                    // Màn hình trắng hoặc Logo loading chờ DataStore đọc (chỉ chớp nhoáng vài ms)
                    // (Sau này bạn có thể làm màn hình Splash Screen xịn xò thay thế vào đây)
                }
            }
        }
    }
}
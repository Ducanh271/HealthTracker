package com.example.healthtracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.data.local.room.dao.MealDao
import com.example.healthtracker.ui.navigation.AppNavGraph
import com.example.healthtracker.ui.theme.HealthTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import androidx.lifecycle.lifecycleScope
import com.example.healthtracker.ui.features.onboarding.OnboardingScreen
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mealDao: MealDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("HiltTest", "Hilt đã hoạt động! MealDao instance: $mealDao")
        lifecycleScope.launch {
            mealDao.getAllFoodItems().collect { foods ->
                Log.d("DatabaseTest", "Số lượng món ăn: ${foods.size}")
            }
        }
        setContent {
            HealthTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph()
//                    OnboardingScreen()
                }
            }
        }
    }
}


package com.example.healthtracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthtracker.ui.features.onboarding.OnboardingScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Onboarding.route
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        Screen.Dashboard.route,
        Screen.MealDiary.route,
        Screen.ActivityDiary.route,
        Screen.Settings.route
    )

    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if(showBottomBar){
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Screen.Onboarding.route) {
                OnboardingScreen(
                    onNavigateToDashboard = {
                        navController.navigate(Screen.Dashboard.route){
                            popUpTo(Screen.Onboarding.route){inclusive = true}
                        }
                    }
                )
            }
            composable(route = Screen.Dashboard.route) {
                Surface(modifier = Modifier.fillMaxSize()) { Text("Dashboard Screen") }
            }

            composable(route = Screen.MealDiary.route) {
                Surface(modifier = Modifier.fillMaxSize()) { Text("Meal Diary Screen") }
            }

            composable(route = Screen.ActivityDiary.route) {
                Surface(modifier = Modifier.fillMaxSize()) { Text("Activity Diary Screen") }
            }

            composable(route = Screen.Settings.route) {
                Surface(modifier = Modifier.fillMaxSize()) { Text("Settings Screen") }
            }
        }
    }

}
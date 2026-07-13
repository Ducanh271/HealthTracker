package com.example.healthtracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            Surface(modifier = Modifier.fillMaxSize()) { Text("Onboarding Screen") }
        }

        composable(route = Screen.Dashboard.route) {
            Surface(modifier = Modifier.fillMaxSize()) { Text("Dashboard Screen") }
        }

        composable(route = Screen.Diary.route){
            Surface(modifier = Modifier.fillMaxSize()){Text("Diary Screen")}
        }

        composable(route = Screen.Statistics.route) {
            Surface(modifier = Modifier.fillMaxSize()) { Text("Statistics Screen") }
        }

        composable(route = Screen.Settings.route) {
            Surface(modifier = Modifier.fillMaxSize()) { Text("Settings Screen") }
        }

    }

}
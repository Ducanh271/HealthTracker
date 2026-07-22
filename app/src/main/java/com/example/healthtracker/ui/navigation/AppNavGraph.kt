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
import com.example.healthtracker.ui.features.dashboard.DashboardScreen
import com.example.healthtracker.ui.features.diary.activity.ActivityDiaryScreen
import com.example.healthtracker.ui.features.diary.meal.MealDiaryScreen
import com.example.healthtracker.ui.features.onboarding.OnboardingScreen
import com.example.healthtracker.ui.features.profile.EditProfileScreen
import com.example.healthtracker.ui.features.settings.SettingsScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
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
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())        ){
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
                Surface(modifier = Modifier.fillMaxSize()) { DashboardScreen() }
            }

            composable(route = Screen.MealDiary.route) {
                Surface(modifier = Modifier.fillMaxSize()) {MealDiaryScreen() }
            }

            composable(route = Screen.ActivityDiary.route) {
                Surface(modifier = Modifier.fillMaxSize()) { ActivityDiaryScreen() }
            }


            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onEditProfileClick = {
                            navController.navigate(Screen.EditProfile.route)
                    }
                )
            }

            composable(route = Screen.EditProfile.route) {
                EditProfileScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

}
package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun insertFoodItem(item: FoodItem): Long
    fun searchFoodItems(searchQuery: String): Flow<List<FoodItem>>
    suspend fun insertMealLog(meal: Meal)
    suspend fun deleteMealLog(meal: Meal)
    fun getMealLogsByDate(date: String): Flow<List<Meal>>
    fun getMealLogsByDates(dates: List<String>): Flow<List<Meal>>
}

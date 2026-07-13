package com.example.healthtracker.domain.repository

import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.FoodItemEntity
import com.example.healthtracker.data.local.room.entity.MealLogEntity
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getAllFoodItems(): Flow<List<FoodItemEntity>>
    suspend fun insertFoodItems(items: List<FoodItemEntity>)
    suspend fun insertMealLog(log: MealLogEntity)
    suspend fun deleteMealLog(log: MealLogEntity)
    fun getMealLogsByDate(date: Long): Flow<List<MealLogEntity>>
}
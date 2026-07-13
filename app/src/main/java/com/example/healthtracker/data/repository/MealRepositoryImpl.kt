package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.room.dao.MealDao
import com.example.healthtracker.data.local.room.entity.FoodItemEntity
import com.example.healthtracker.data.local.room.entity.MealLogEntity
import com.example.healthtracker.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealDao: MealDao
): MealRepository {
    override fun getAllFoodItems(): Flow<List<FoodItemEntity>> = mealDao.getAllFoodItems()
    override suspend fun insertFoodItems(items: List<FoodItemEntity>) = mealDao.insertFoodItems(items)
    override suspend fun insertMealLog(log: MealLogEntity) = mealDao.insertMealLog(log)
    override suspend fun deleteMealLog(log: MealLogEntity) = mealDao.deleteMealLog(log)
    override fun getMealLogsByDate(date: Long): Flow<List<MealLogEntity>> = mealDao.getMealLogsByDate(date)
}
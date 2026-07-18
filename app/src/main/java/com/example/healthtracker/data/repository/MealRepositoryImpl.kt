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
    override suspend fun insertFoodItem(item: FoodItemEntity): Long = mealDao.insertFoodItem(item)
    override fun searchFoodItems(searchQuery: String): Flow<List<FoodItemEntity>> = mealDao.searchFoodItems(searchQuery)
    override suspend fun insertMealLog(log: MealLogEntity) = mealDao.insertMealLog(log)
    override suspend fun deleteMealLog(log: MealLogEntity) = mealDao.deleteMealLog(log)
    override fun getMealLogsByDate(date: String): Flow<List<MealLogEntity>> = mealDao.getMealLogsByDate(date)

    override fun getMealLogsByDates(dates: List<String>): Flow<List<MealLogEntity>> {
        return mealDao.getMealLogsByDates(dates)
    }
}
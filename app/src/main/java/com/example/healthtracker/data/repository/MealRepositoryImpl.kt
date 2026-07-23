package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.room.dao.MealDao
import com.example.healthtracker.data.mapper.toDomain
import com.example.healthtracker.data.mapper.toEntity
import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealDao: MealDao
): MealRepository {
    override suspend fun insertFoodItem(item: FoodItem): Long =
        mealDao.insertFoodItem(item.toEntity())

    override fun searchFoodItems(searchQuery: String): Flow<List<FoodItem>> =
        mealDao.searchFoodItems(searchQuery).map { entities -> entities.map { it.toDomain() } }

    override suspend fun insertMealLog(meal: Meal) =
        mealDao.insertMealLog(meal.toEntity())

    override suspend fun deleteMealLog(meal: Meal) =
        mealDao.deleteMealLog(meal.toEntity())

    override fun getMealLogsByDate(date: String): Flow<List<Meal>> =
        mealDao.getMealLogsByDate(date).map { entities -> entities.map { it.toDomain() } }

    override fun getMealLogsByDates(dates: List<String>): Flow<List<Meal>> =
        mealDao.getMealLogsByDates(dates).map { entities -> entities.map { it.toDomain() } }
}

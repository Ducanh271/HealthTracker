package com.example.healthtracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthtracker.data.local.room.entity.FoodItemEntity
import com.example.healthtracker.data.local.room.entity.MealLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("Select * from food_items")
    fun getAllFoodItems(): Flow<List<FoodItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItems(items: List<FoodItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItem(item: FoodItemEntity): Long

    @Query("SELECT * FROM food_items WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchFoodItems(searchQuery: String): Flow<List<FoodItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealLog(log: MealLogEntity)

    @Delete
    suspend fun deleteMealLog(log: MealLogEntity)

    @Query("select * from meal_logs where date = :date")
    fun getMealLogsByDate(date: String): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs WHERE date IN (:dates)")
    fun getMealLogsByDates(dates: List<String>): Flow<List<MealLogEntity>>


}

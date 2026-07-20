package com.example.healthtracker.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthtracker.data.local.room.dao.ActivityDao
import com.example.healthtracker.data.local.room.dao.MealDao
import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.ActivityLogEntity
import com.example.healthtracker.data.local.room.entity.FoodItemEntity
import com.example.healthtracker.data.local.room.entity.MealLogEntity

@Database(
    entities = [
        MealLogEntity::class,
        FoodItemEntity::class,
        ActivityItemEntity::class,
        ActivityLogEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class HealthDatabase : RoomDatabase() {
    abstract val mealDao: MealDao
    abstract val activityDao: ActivityDao

}
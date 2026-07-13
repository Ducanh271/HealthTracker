package com.example.healthtracker.data.local.room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class HealthDatabaseCallback @Inject constructor(
    private val databaseProvider: Provider<HealthDatabase>
) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase){
        super.onCreate(db)
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch{
            val dbInstance = databaseProvider.get()
            dbInstance.mealDao.insertFoodItems(SeedData.predefinedFoods)
            dbInstance.activityDao.insertActivityItems(SeedData.predefinedActivities)
        }
    }
}
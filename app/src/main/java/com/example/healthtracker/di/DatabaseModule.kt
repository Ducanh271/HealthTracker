package com.example.healthtracker.di

import android.content.Context
import androidx.room.Room
import com.example.healthtracker.data.local.room.HealthDatabase
import com.example.healthtracker.data.local.room.HealthDatabaseCallback
import com.example.healthtracker.data.local.room.dao.ActivityDao
import com.example.healthtracker.data.local.room.dao.MealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideHealthDatabase(
        @ApplicationContext context: Context,
        callback: HealthDatabaseCallback
    ): HealthDatabase {
        return Room.databaseBuilder(
            context, HealthDatabase::class.java, "health_tracker_db"
        )
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(database: HealthDatabase): MealDao{
        return database.mealDao
    }

    @Provides
    @Singleton
    fun provideActivityDao(database: HealthDatabase): ActivityDao{
        return database.activityDao
    }

}
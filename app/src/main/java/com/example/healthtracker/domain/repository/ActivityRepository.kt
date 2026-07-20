package com.example.healthtracker.domain.repository

import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.ActivityLogEntity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun getAllActivityItems(): Flow<List<ActivityItemEntity>>
    suspend fun insertActivityItems(items: List<ActivityItemEntity>)
    suspend fun insertActivityLog(log: ActivityLogEntity)
    suspend fun deleteActivityLog(log: ActivityLogEntity)
    fun getActivityLogsByDate(date: String): Flow<List<ActivityLogEntity>>

    fun getActivityLogsByDates(dates: List<String>): Flow<List<ActivityLogEntity>>

    fun searchActivityItems(query: String): Flow<List<ActivityItemEntity>>
    suspend fun deleteActivityLogById(id: Int)
    suspend fun updateActivityLog(id: Int, duration: Int, calories: Int)
}
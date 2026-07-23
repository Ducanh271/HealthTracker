package com.example.healthtracker.domain.repository

import com.example.healthtracker.domain.model.ActivityCatalogEntry
import com.example.healthtracker.domain.model.ActivityLog
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun insertActivityLog(log: ActivityLog)
    fun getActivityLogsByDate(date: String): Flow<List<ActivityLog>>
    fun getActivityLogsByDates(dates: List<String>): Flow<List<ActivityLog>>
    fun searchActivityItems(query: String): Flow<List<ActivityCatalogEntry>>
    suspend fun deleteActivityLogById(id: Int)
    suspend fun updateActivityLog(id: Int, duration: Int, calories: Int)
}

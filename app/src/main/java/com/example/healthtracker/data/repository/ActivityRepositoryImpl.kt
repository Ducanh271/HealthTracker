package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.room.dao.ActivityDao
import com.example.healthtracker.data.mapper.toDomain
import com.example.healthtracker.data.mapper.toEntity
import com.example.healthtracker.domain.model.ActivityCatalogEntry
import com.example.healthtracker.domain.model.ActivityLog
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao
): ActivityRepository {
    override suspend fun insertActivityLog(log: ActivityLog) =
        activityDao.insertActivityLog(log.toEntity())

    override fun getActivityLogsByDate(date: String): Flow<List<ActivityLog>> =
        activityDao.getActivityLogsByDate(date).map { entities -> entities.map { it.toDomain() } }

    override fun getActivityLogsByDates(dates: List<String>): Flow<List<ActivityLog>> =
        activityDao.getActivityLogsByDates(dates).map { entities -> entities.map { it.toDomain() } }

    override fun searchActivityItems(query: String): Flow<List<ActivityCatalogEntry>> =
        activityDao.searchActivityItems(query).map { entities -> entities.map { it.toDomain() } }

    override suspend fun deleteActivityLogById(id: Int) {
        activityDao.deleteActivityLogById(id)
    }

    override suspend fun updateActivityLog(id: Int, duration: Int, calories: Int) {
        activityDao.updateActivityLog(id, duration, calories)
    }
}

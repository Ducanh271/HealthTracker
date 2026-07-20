package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.room.dao.ActivityDao
import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.ActivityLogEntity
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao
): ActivityRepository {
    override fun getAllActivityItems(): Flow<List<ActivityItemEntity>> = activityDao.getAllActivityItems()

    override suspend fun insertActivityItems(items: List<ActivityItemEntity>) = activityDao.insertActivityItems(items)

    override suspend fun insertActivityLog(log: ActivityLogEntity) = activityDao.insertActivityLog(log)

    override suspend fun deleteActivityLog(log: ActivityLogEntity) = activityDao.deleteActivityLog(log)

    override fun getActivityLogsByDate(date: String): Flow<List<ActivityLogEntity>> = activityDao.getActivityLogsByDate(date)

    override fun getActivityLogsByDates(dates: List<String>): Flow<List<ActivityLogEntity>> {
        return activityDao.getActivityLogsByDates(dates)
    }

    override fun searchActivityItems(query: String): Flow<List<ActivityItemEntity>> = activityDao.searchActivityItems(query)

    override suspend fun deleteActivityLogById(id: Int) {
        activityDao.deleteActivityLogById(id)
    }

    override suspend fun updateActivityLog(id: Int, duration: Int, calories: Int) {
        activityDao.updateActivityLog(id, duration, calories)
    }
}
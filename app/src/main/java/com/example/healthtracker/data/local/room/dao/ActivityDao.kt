package com.example.healthtracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.ActivityLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("select * from activity_items")
    fun getAllActivityItems(): Flow<List<ActivityItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityItems(items: List<ActivityItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: ActivityLogEntity)

    @Delete
    suspend fun deleteActivityLog(log: ActivityLogEntity)

    @Query("SELECT * FROM activity_logs WHERE date = :date")
    fun getActivityLogsByDate(date: String): Flow<List<ActivityLogEntity>>

    @Query("SELECT * FROM activity_logs WHERE date IN (:dates)")
    fun getActivityLogsByDates(dates: List<String>): Flow<List<ActivityLogEntity>>

    @Query("SELECT * FROM activity_items WHERE name LIKE '%' || :query || '%'")
    fun searchActivityItems(query: String): Flow<List<ActivityItemEntity>>

    @Query(
        """
        SELECT items.* FROM activity_items AS items
        INNER JOIN (
            SELECT activityName, MAX(id) AS lastLogId
            FROM activity_logs
            GROUP BY activityName
        ) AS recent ON recent.activityName = items.name
        ORDER BY recent.lastLogId DESC
        LIMIT :limit
        """
    )
    fun getRecentActivityItems(limit: Int): Flow<List<ActivityItemEntity>>

    @Query("SELECT * FROM activity_items LIMIT :limit")
    fun getDefaultActivityItems(limit: Int): Flow<List<ActivityItemEntity>>

    @Query("SELECT * FROM activity_items WHERE name = :name LIMIT 1")
    suspend fun getActivityItemByName(name: String): ActivityItemEntity?

    @Query("DELETE FROM activity_logs WHERE id = :id")
    suspend fun deleteActivityLogById(id: Int)

    @Query("UPDATE activity_logs SET durationMinutes = :duration, caloriesBurned = :calories WHERE id = :id")
    suspend fun updateActivityLog(id: Int, duration: Int, calories: Int)

}
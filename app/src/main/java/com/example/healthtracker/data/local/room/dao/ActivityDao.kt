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
    fun getActivityLogsByDate(date: Long): Flow<List<ActivityLogEntity>>

}
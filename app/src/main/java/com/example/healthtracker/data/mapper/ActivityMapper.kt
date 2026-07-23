package com.example.healthtracker.data.mapper

import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.ActivityLogEntity
import com.example.healthtracker.domain.model.ActivityCatalogEntry
import com.example.healthtracker.domain.model.ActivityLog

fun ActivityLogEntity.toDomain(): ActivityLog {
    return ActivityLog(
        id = this.id,
        date = this.date,
        activityName = this.activityName,
        durationMinutes = this.durationMinutes,
        caloriesBurned = this.caloriesBurned
    )
}

fun ActivityLog.toEntity(): ActivityLogEntity {
    return ActivityLogEntity(
        id = this.id,
        date = this.date,
        activityName = this.activityName,
        durationMinutes = this.durationMinutes,
        caloriesBurned = this.caloriesBurned
    )
}

fun ActivityItemEntity.toDomain(): ActivityCatalogEntry {
    return ActivityCatalogEntry(
        id = this.id,
        name = this.name,
        metValue = this.metValue
    )
}

package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.data.local.room.entity.ActivityLogEntity
import com.example.healthtracker.domain.repository.ActivityRepository
import javax.inject.Inject

class AddActivityLogUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(
        date: String,
        name: String,
        duration: Int,
        calories: Int
    ) {
        val entity = ActivityLogEntity(
            date = date,
            activityName = name,
            durationMinutes = duration,
            caloriesBurned = calories
        )
        activityRepository.insertActivityLog(entity)
    }
}
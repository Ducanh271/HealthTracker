package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.model.ActivityLog
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
        val log = ActivityLog(
            date = date,
            activityName = name,
            durationMinutes = duration,
            caloriesBurned = calories
        )
        activityRepository.insertActivityLog(log)
    }
}

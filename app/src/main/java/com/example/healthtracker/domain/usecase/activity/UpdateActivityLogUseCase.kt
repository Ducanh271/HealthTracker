package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.repository.ActivityRepository
import javax.inject.Inject

class UpdateActivityLogUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(id: Int, duration: Int, calories: Int) {
        activityRepository.updateActivityLog(id, duration, calories)
    }
}
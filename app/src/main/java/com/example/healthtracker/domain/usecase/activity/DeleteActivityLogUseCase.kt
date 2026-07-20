package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteActivityLogUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(activityId: Int) {
        repository.deleteActivityLogById(activityId)
    }
}
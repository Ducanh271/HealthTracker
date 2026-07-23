package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.model.ActivityItem
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class ActivityDiarySummary(
    val totalCalories: Int,
    val logs: List<ActivityItem>
)

class GetActivityDiaryUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(date: String): Flow<ActivityDiarySummary> {
        return activityRepository.getActivityLogsByDate(date).map { logs ->
            val totalCal = logs.sumOf { it.caloriesBurned }

            val mappedLogs = logs.map { log ->
                ActivityItem(
                    id = log.id,
                    name = log.activityName,
                    duration = log.durationMinutes,
                    time = "",
                    caloriesBurned = log.caloriesBurned,
                    type = ActivityType.CARDIO
                )
            }
            ActivityDiarySummary(totalCal, mappedLogs)
        }
    }
}
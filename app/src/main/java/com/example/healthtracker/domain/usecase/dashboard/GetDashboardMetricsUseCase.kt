package com.example.healthtracker.domain.usecase.dashboard

import com.example.healthtracker.domain.model.DashboardMetrics
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.MealRepository
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetDashboardMetricsUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DashboardMetrics> {
        val last7Days = DateUtils.getLast7DaysStrings()
        val todayStr = DateUtils.getTodayString()

        return combine(
            userRepository.userProfile,
            mealRepository.getMealLogsByDates(last7Days),
            activityRepository.getActivityLogsByDates(last7Days)
        ) { profile, mealLogs, activityLogs ->

            val consumedByDate = mealLogs
                .groupBy { it.date }
                .mapValues { entry -> entry.value.sumOf { it.totalCalories } }

            val todayConsumed = consumedByDate[todayStr] ?: 0
            val todayBurned = activityLogs.filter { it.date == todayStr }.sumOf { it.caloriesBurned }
            val targetKcal = profile.tdee

            var totalConsumedWeek = 0
            var totalBurnedWeek = 0
            var goalReachedCount = 0

            for (date in last7Days) {
                val dailyConsumed = consumedByDate[date] ?: 0
                val dailyBurned = activityLogs.filter { it.date == date }.sumOf { it.caloriesBurned }

                totalConsumedWeek += dailyConsumed
                totalBurnedWeek += dailyBurned

                if (dailyConsumed > 0 && dailyConsumed <= (targetKcal + dailyBurned)) {
                    goalReachedCount++
                }
            }

            DashboardMetrics(
                targetCalories = targetKcal,
                consumedCalories = todayConsumed,
                burnedCalories = todayBurned,
                avgConsumed = totalConsumedWeek / 7,
                avgBurned = totalBurnedWeek / 7,
                goalReachedDays = goalReachedCount
            )
        }
    }
}

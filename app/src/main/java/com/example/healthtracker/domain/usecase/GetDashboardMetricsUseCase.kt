package com.example.healthtracker.domain.usecase

import com.example.healthtracker.data.local.datastore.SettingsDataStore
import com.example.healthtracker.domain.model.DashboardMetrics
import com.example.healthtracker.domain.repository.ActivityRepository
import com.example.healthtracker.domain.repository.MealRepository
import com.example.healthtracker.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetDashboardMetricsUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val activityRepository: ActivityRepository,
    private val settingsDataStore: SettingsDataStore
) {
    operator fun invoke(): Flow<DashboardMetrics> {
        val last7Days = DateUtils.getLast7DaysStrings()
        val todayStr = DateUtils.getTodayString()

        return combine(
            settingsDataStore.userProfile,
            mealRepository.getMealLogsByDates(last7Days),
            activityRepository.getActivityLogsByDates(last7Days)
        ) { profile, mealLogs, activityLogs ->

            val todayConsumed = mealLogs.filter { it.date == todayStr }.sumOf { it.totalCalories }
            val todayBurned = activityLogs.filter { it.date == todayStr }.sumOf { it.caloriesBurned }
            val targetKcal = profile.tdee

            val maxCalorieInWeek = mealLogs
                .groupBy { it.date }
                .mapValues { entry -> entry.value.sumOf { it.totalCalories } }
                .values.maxOrNull()?.coerceAtLeast(targetKcal) ?: targetKcal

            val chartData = mutableListOf<Pair<String, Float>>()
            var totalConsumedWeek = 0
            var totalBurnedWeek = 0
            var goalReachedCount = 0

            for (date in last7Days.reversed()) {
                val dayLabel = DateUtils.getDayOfWeekVN(date)
                val dailyConsumed = mealLogs.filter { it.date == date }.sumOf { it.totalCalories }
                val dailyBurned = activityLogs.filter { it.date == date }.sumOf { it.caloriesBurned }

                totalConsumedWeek += dailyConsumed
                totalBurnedWeek += dailyBurned

                if (dailyConsumed > 0 && dailyConsumed <= (targetKcal + dailyBurned)) {
                    goalReachedCount++
                }

                val progress = if (maxCalorieInWeek > 0) {
                    (dailyConsumed.toFloat() / maxCalorieInWeek.toFloat()).coerceIn(0f, 1f)
                } else 0f

                chartData.add(dayLabel to progress)
            }

            DashboardMetrics(
                targetCalories = targetKcal,
                consumedCalories = todayConsumed,
                burnedCalories = todayBurned,
                weeklyChartData = chartData,
                avgConsumed = totalConsumedWeek / 7,
                avgBurned = totalBurnedWeek / 7,
                goalReachedDays = goalReachedCount
            )
        }
    }
}
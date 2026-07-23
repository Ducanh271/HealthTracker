package com.example.healthtracker.domain.usecase.dashboard

import com.example.healthtracker.domain.model.DailyIntakePoint
import com.example.healthtracker.domain.repository.MealRepository
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWeeklyIntakeChartUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(weekOffset: Int): Flow<List<DailyIntakePoint>> {
        val weekDates = DateUtils.getLastNDaysStrings(7, weekOffset)

        return combine(
            userRepository.userProfile,
            mealRepository.getMealLogsByDates(weekDates)
        ) { profile, mealLogs ->
            val consumedByDate = mealLogs
                .groupBy { it.date }
                .mapValues { entry -> entry.value.sumOf { it.totalCalories } }

            val maxCalorie = consumedByDate.values.maxOrNull()
                ?.coerceAtLeast(profile.tdee) ?: profile.tdee

            weekDates.map { date ->
                val calories = consumedByDate[date] ?: 0
                DailyIntakePoint(
                    date = date,
                    calories = calories,
                    progress = if (maxCalorie > 0) {
                        (calories.toFloat() / maxCalorie.toFloat()).coerceIn(0f, 1f)
                    } else 0f
                )
            }
        }
    }
}

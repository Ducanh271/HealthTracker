package com.example.healthtracker.domain.usecase.dashboard

import com.example.healthtracker.domain.model.WeeklyTrendPoint
import com.example.healthtracker.domain.repository.MealRepository
import com.example.healthtracker.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeeklyTrendUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(weekOffset: Int): Flow<List<WeeklyTrendPoint>> {
        val trendDates = DateUtils.getLastNDaysStrings(DAYS_PER_WEEK * TREND_WEEKS, weekOffset)

        return mealRepository.getMealLogsByDates(trendDates).map { mealLogs ->
            val consumedByDate = mealLogs
                .groupBy { it.date }
                .mapValues { entry -> entry.value.sumOf { it.totalCalories } }

            trendDates.chunked(DAYS_PER_WEEK).map { weekDates ->
                WeeklyTrendPoint(
                    weekStartDate = weekDates.first(),
                    avgCalories = weekDates.sumOf { consumedByDate[it] ?: 0 } / DAYS_PER_WEEK
                )
            }
        }
    }

    companion object {
        private const val DAYS_PER_WEEK = 7
        private const val TREND_WEEKS = 4
    }
}

package com.example.healthtracker.ui.features.dashboard

import com.example.healthtracker.domain.model.AdviceType
import com.example.healthtracker.domain.model.DailyIntakePoint
import com.example.healthtracker.domain.model.WeeklyTrendPoint

data class DashboardState(
    val currentDate: String = "",
    val targetCalories: Int = 0,
    val consumedCalories: Int = 0,
    val burnedCalories: Int = 0,
    val remainingCalories: Int = 0,
    val balanceCalories: Int = 0,
    val adviceType: AdviceType = AdviceType.START_DAY,
    val weeklyBarData: List<DailyIntakePoint> = emptyList(),
    val weeklyLineData: List<WeeklyTrendPoint> = emptyList(),
    val barWeekOffset: Int = 0,
    val trendWeekOffset: Int = 0,
    val stats: StatsData = StatsData()
)

sealed class DashboardEvent {
    data object PreviousBarWeek : DashboardEvent()
    data object NextBarWeek : DashboardEvent()
    data object PreviousTrendWeek : DashboardEvent()
    data object NextTrendWeek : DashboardEvent()
}

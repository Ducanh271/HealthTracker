package com.example.healthtracker.ui.features.dashboard

import com.example.healthtracker.domain.model.AdviceType

data class DashboardState(
    val currentDate: String = "",
    val targetCalories: Int = 0,
    val consumedCalories: Int = 0,
    val burnedCalories: Int = 0,
    val remainingCalories: Int = 0,
    val balanceCalories: Int = 0,
    val adviceType: AdviceType = AdviceType.START_DAY,
    val weeklyBarData: List<Pair<String, Float>> = emptyList(),
    val weeklyLineData: List<Pair<String, Float>> = emptyList(),
    val stats: StatsData = StatsData()
)
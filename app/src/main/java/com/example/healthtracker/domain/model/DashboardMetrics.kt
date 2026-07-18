package com.example.healthtracker.domain.model


data class DashboardMetrics(
    val targetCalories: Int,
    val consumedCalories: Int,
    val burnedCalories: Int,
    val weeklyChartData: List<Pair<String, Float>>,
    val avgConsumed: Int,
    val avgBurned: Int,
    val goalReachedDays: Int
) {
    val balanceCalories: Int
        get() = consumedCalories - burnedCalories

    val remainingCalories: Int
        get() = targetCalories - balanceCalories
}
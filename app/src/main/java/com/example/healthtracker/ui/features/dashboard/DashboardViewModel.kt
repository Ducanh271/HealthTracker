package com.example.healthtracker.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.usecase.dashboard.GetDashboardAdviceUseCase
import com.example.healthtracker.domain.usecase.dashboard.GetDashboardMetricsUseCase
import com.example.healthtracker.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardMetricsUseCase: GetDashboardMetricsUseCase,
    private val getDashboardAdviceUseCase: GetDashboardAdviceUseCase // Inject UseCase advice
) : ViewModel() {

    val state: StateFlow<DashboardState> = getDashboardMetricsUseCase().map { metrics ->



        DashboardState(
            currentDate = DateUtils.getTodayDisplayString(),
            targetCalories = metrics.targetCalories,
            consumedCalories = metrics.consumedCalories,
            burnedCalories = metrics.burnedCalories,
            remainingCalories = metrics.remainingCalories,
            balanceCalories = metrics.balanceCalories,
            adviceText = getDashboardAdviceUseCase(metrics.consumedCalories, metrics.remainingCalories),
            weeklyBarData = metrics.weeklyChartData,
            weeklyLineData = metrics.weeklyChartData,
            stats = StatsData(
                avgConsumed = metrics.avgConsumed,
                avgBurned = metrics.avgBurned,
                goalReachedDays = metrics.goalReachedDays
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )
}
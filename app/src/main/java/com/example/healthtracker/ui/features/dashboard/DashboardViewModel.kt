package com.example.healthtracker.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.usecase.GetDashboardMetricsUseCase
import com.example.healthtracker.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardMetricsUseCase: GetDashboardMetricsUseCase
) : ViewModel() {

    val state: StateFlow<DashboardState> = getDashboardMetricsUseCase().map { metrics ->

        val advice = when {
            metrics.consumedCalories == 0 -> "Bắt đầu ngày mới! Hãy ghi lại bữa ăn đầu tiên của bạn."
            metrics.remainingCalories < 0 -> "Cảnh báo: Bạn đã vượt quá lượng calo mục tiêu hôm nay!"
            metrics.remainingCalories < 200 -> "Tuyệt vời! Bạn đã sắp đạt mục tiêu calo hôm nay."
            else -> "Bạn đang làm rất tốt! Hãy tiếp tục duy trì nhé."
        }

        DashboardState(
            currentDate = DateUtils.getTodayDisplayString(),
            targetCalories = metrics.targetCalories,
            consumedCalories = metrics.consumedCalories,
            burnedCalories = metrics.burnedCalories,
            remainingCalories = metrics.remainingCalories,
            balanceCalories = metrics.balanceCalories,
            adviceText = advice,
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
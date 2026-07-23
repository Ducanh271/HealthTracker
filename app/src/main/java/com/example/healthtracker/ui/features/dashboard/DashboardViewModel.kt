package com.example.healthtracker.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.usecase.dashboard.GetDashboardAdviceUseCase
import com.example.healthtracker.domain.usecase.dashboard.GetDashboardMetricsUseCase
import com.example.healthtracker.domain.usecase.dashboard.GetWeeklyIntakeChartUseCase
import com.example.healthtracker.domain.usecase.dashboard.GetWeeklyTrendUseCase
import com.example.healthtracker.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardMetricsUseCase: GetDashboardMetricsUseCase,
    private val getDashboardAdviceUseCase: GetDashboardAdviceUseCase,
    private val getWeeklyIntakeChartUseCase: GetWeeklyIntakeChartUseCase,
    private val getWeeklyTrendUseCase: GetWeeklyTrendUseCase
) : ViewModel() {

    private val barWeekOffset = MutableStateFlow(0)
    private val trendWeekOffset = MutableStateFlow(0)

    private val barChartFlow = barWeekOffset.flatMapLatest { offset ->
        getWeeklyIntakeChartUseCase(offset).map { offset to it }
    }

    private val trendChartFlow = trendWeekOffset.flatMapLatest { offset ->
        getWeeklyTrendUseCase(offset).map { offset to it }
    }

    val state: StateFlow<DashboardState> = combine(
        getDashboardMetricsUseCase(),
        barChartFlow,
        trendChartFlow
    ) { metrics, (barOffset, barData), (trendOffset, trendData) ->
        DashboardState(
            currentDate = DateUtils.getTodayDisplayString(),
            targetCalories = metrics.targetCalories,
            consumedCalories = metrics.consumedCalories,
            burnedCalories = metrics.burnedCalories,
            remainingCalories = metrics.remainingCalories,
            balanceCalories = metrics.balanceCalories,
            adviceType = getDashboardAdviceUseCase(metrics.consumedCalories, metrics.remainingCalories),
            weeklyBarData = barData,
            weeklyLineData = trendData,
            barWeekOffset = barOffset,
            trendWeekOffset = trendOffset,
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

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.PreviousBarWeek -> barWeekOffset.update { it + 1 }
            DashboardEvent.NextBarWeek -> barWeekOffset.update { (it - 1).coerceAtLeast(0) }
            DashboardEvent.PreviousTrendWeek -> trendWeekOffset.update { it + 1 }
            DashboardEvent.NextTrendWeek -> trendWeekOffset.update { (it - 1).coerceAtLeast(0) }
        }
    }
}

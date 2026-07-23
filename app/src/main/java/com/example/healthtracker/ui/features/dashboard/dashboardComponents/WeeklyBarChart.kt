package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailyIntakePoint
import com.example.healthtracker.ui.theme.LocalDimens
import com.example.healthtracker.utils.DateUtils

@Composable
fun dayOfWeekLabel(dateString: String): String {
    val resId = when (DateUtils.getDayOfWeekNumber(dateString)) {
        1 -> R.string.day_mon
        2 -> R.string.day_tue
        3 -> R.string.day_wed
        4 -> R.string.day_thu
        5 -> R.string.day_fri
        6 -> R.string.day_sat
        else -> R.string.day_sun
    }
    return stringResource(id = resId)
}

@Composable
fun WeeklyBarChart(
    weeklyData: List<DailyIntakePoint>,
    weekOffset: Int,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit
) {
    val dimens = LocalDimens.current
    val todayStr = remember { DateUtils.getTodayString() }
    val subtitle = if (weekOffset == 0 || weeklyData.isEmpty()) {
        stringResource(id = R.string.dash_chart_subtitle)
    } else {
        "${DateUtils.formatShortDayMonth(weeklyData.first().date)} – ${DateUtils.formatShortDayMonth(weeklyData.last().date)}"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(dimens.lg)
    ) {
        ChartWeekHeader(
            title = stringResource(id = R.string.dash_chart_bar_title),
            subtitle = subtitle,
            canGoForward = weekOffset > 0,
            onPrevious = onPreviousWeek,
            onNext = onNextWeek
        )

        Spacer(modifier = Modifier.height(dimens.lg))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.chartBarHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            weeklyData.forEach { point ->
                val isToday = point.date == todayStr
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (point.calories > 0) {
                                Text(
                                    text = point.calories.toString(),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isToday) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(dimens.chartBarWidthFraction)
                                    .fillMaxHeight(point.progress.coerceIn(dimens.chartBarMinHeightFraction, 1f))
                                    .clip(RoundedCornerShape(topStart = dimens.chartBarCorner, topEnd = dimens.chartBarCorner))
                                    .background(
                                        if (isToday) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(dimens.xs))
                    Text(
                        text = dayOfWeekLabel(dateString = point.date),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal),
                        color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeeklyBarChartPreview() {
    MaterialTheme {
        val mockData = listOf(
            DailyIntakePoint("2026-07-17", 1450, 0.6f),
            DailyIntakePoint("2026-07-18", 2050, 0.85f),
            DailyIntakePoint("2026-07-19", 1090, 0.45f),
            DailyIntakePoint("2026-07-20", 1690, 0.7f),
            DailyIntakePoint("2026-07-21", 2170, 0.9f),
            DailyIntakePoint("2026-07-22", 0, 0.1f),
            DailyIntakePoint("2026-07-23", 2410, 1f)
        )
        WeeklyBarChart(weeklyData = mockData, weekOffset = 0, onPreviousWeek = {}, onNextWeek = {})
    }
}

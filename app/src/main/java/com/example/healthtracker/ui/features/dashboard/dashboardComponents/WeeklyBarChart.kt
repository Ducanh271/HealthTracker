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
fun WeeklyBarChart(weeklyData: List<Pair<String, Float>>) {
    val dimens = LocalDimens.current
    val todayStr = remember { DateUtils.getTodayString() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(dimens.lg)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(id = R.string.dash_chart_bar_title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = R.string.dash_chart_subtitle),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(dimens.lg))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.chartBarHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            weeklyData.forEach { (date, progress) ->
                val isToday = date == todayStr
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(dimens.chartBarWidthFraction)
                            .fillMaxHeight(progress.coerceIn(dimens.chartBarMinHeightFraction, 1f))
                            .clip(RoundedCornerShape(topStart = dimens.chartBarCorner, topEnd = dimens.chartBarCorner))
                            .background(
                                if (isToday) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )
                    )
                    Spacer(modifier = Modifier.height(dimens.xs))
                    Text(
                        text = dayOfWeekLabel(dateString = date),
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
            "2026-07-20" to 0.6f, "2026-07-21" to 0.85f, "2026-07-22" to 0.45f,
            "2026-07-23" to 0.7f, "2026-07-24" to 0.9f, "2026-07-25" to 0.1f, "2026-07-26" to 0.1f
        )
        WeeklyBarChart(weeklyData = mockData)
    }
}

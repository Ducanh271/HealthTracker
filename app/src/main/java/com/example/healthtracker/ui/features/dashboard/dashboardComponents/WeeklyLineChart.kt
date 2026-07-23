package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.WeeklyTrendPoint
import com.example.healthtracker.ui.theme.LocalDimens
import com.example.healthtracker.utils.DateUtils

@Composable
fun WeeklyLineChart(
    weeklyData: List<WeeklyTrendPoint>,
    weekOffset: Int,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit
) {
    val dimens = LocalDimens.current
    val primaryColor = MaterialTheme.colorScheme.primary
    val subtitle = if (weekOffset == 0 || weeklyData.isEmpty()) {
        stringResource(id = R.string.dash_chart_line_subtitle)
    } else {
        "${DateUtils.formatShortDayMonth(weeklyData.first().weekStartDate)} – " +
                DateUtils.formatShortDayMonth(DateUtils.addDays(weeklyData.last().weekStartDate, 6))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(dimens.lg)
    ) {
        ChartWeekHeader(
            title = stringResource(id = R.string.dash_chart_line_title),
            subtitle = subtitle,
            canGoForward = weekOffset > 0,
            onPrevious = onPreviousWeek,
            onNext = onNextWeek
        )

        Spacer(modifier = Modifier.height(dimens.lg))

        if (weeklyData.size < 2) return@Column

        val maxCalories = weeklyData.maxOf { it.avgCalories }.coerceAtLeast(1)

        Canvas(modifier = Modifier.fillMaxWidth().height(dimens.chartLineHeight)) {
            val slotWidth = size.width / weeklyData.size
            val dotRadius = dimens.chartLineDotRadius.toPx()
            val inset = dotRadius + dimens.chartLineStroke.toPx()
            val drawableHeight = size.height - inset * 2

            val points = weeklyData.mapIndexed { index, point ->
                Offset(
                    x = slotWidth * (index + 0.5f),
                    y = inset + (1f - point.avgCalories.toFloat() / maxCalories) * drawableHeight
                )
            }

            val path = Path()
            points.forEachIndexed { index, offset ->
                if (index == 0) path.moveTo(offset.x, offset.y) else path.lineTo(offset.x, offset.y)
            }

            drawPath(
                path = path,
                color = primaryColor,
                style = Stroke(width = dimens.chartLineStroke.toPx())
            )
            points.forEach { offset ->
                drawCircle(color = primaryColor, radius = dotRadius, center = offset)
            }
        }

        Spacer(modifier = Modifier.height(dimens.xs))

        Row(modifier = Modifier.fillMaxWidth()) {
            weeklyData.forEachIndexed { index, point ->
                val isLatestWeek = weekOffset == 0 && index == weeklyData.lastIndex
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = point.avgCalories.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isLatestWeek) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isLatestWeek) primaryColor else MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = DateUtils.formatShortDayMonth(point.weekStartDate),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeeklyLineChartPreview() {
    MaterialTheme {
        val mockData = listOf(
            WeeklyTrendPoint("2026-06-26", 1650),
            WeeklyTrendPoint("2026-07-03", 1820),
            WeeklyTrendPoint("2026-07-10", 1540),
            WeeklyTrendPoint("2026-07-17", 1930)
        )
        WeeklyLineChart(weeklyData = mockData, weekOffset = 0, onPreviousWeek = {}, onNextWeek = {})
    }
}

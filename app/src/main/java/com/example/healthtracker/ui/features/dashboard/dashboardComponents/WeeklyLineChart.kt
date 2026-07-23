package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun WeeklyLineChart(weeklyData: List<Pair<String, Float>>) {
    val dimens = LocalDimens.current
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(dimens.lg)
    ) {
        Text(
            text = stringResource(id = R.string.dash_chart_line_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(dimens.lg))

        Canvas(modifier = Modifier.fillMaxWidth().height(dimens.chartLineHeight)) {
            if (weeklyData.size < 2) return@Canvas
            val path = Path()
            val spaceBetweenPoints = size.width / (weeklyData.size - 1)

            weeklyData.forEachIndexed { index, pair ->
                val x = index * spaceBetweenPoints
                val y = size.height - (pair.second * size.height)
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            drawPath(
                path = path,
                color = primaryColor,
                style = Stroke(width = dimens.chartLineStroke.toPx())
            )
        }
    }
}
package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.ui.features.dashboard.StatsData
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun WeeklyStatsRow(stats: StatsData) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = dimens.lg, horizontal = dimens.sm),
        horizontalArrangement = Arrangement.spacedBy(dimens.sm)
    ) {
        StatItem(
            modifier = Modifier.weight(1f),
            value = stats.avgConsumed.toString(),
            label = stringResource(id = R.string.dash_stat_avg_consumed)
        )
        StatItem(
            modifier = Modifier.weight(1f),
            value = stats.avgBurned.toString(),
            label = stringResource(id = R.string.dash_stat_avg_burned)
        )
        StatItem(
            modifier = Modifier.weight(1f),
            value = stats.goalReachedDays.toString(),
            label = stringResource(id = R.string.dash_stat_goal_reached)
        )
    }
}

@Composable
private fun StatItem(modifier: Modifier = Modifier, value: String, label: String) {
    val dimens = LocalDimens.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(dimens.xs))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeeklyStatsRowPreview() {
    MaterialTheme {
        WeeklyStatsRow(stats = StatsData(avgConsumed = 1450, avgBurned = 320, goalReachedDays = 5))
    }
}

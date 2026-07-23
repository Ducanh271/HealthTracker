package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun ChartWeekHeader(
    title: String,
    subtitle: String,
    canGoForward: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(dimens.iconMedium)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(id = R.string.dash_chart_prev_cd),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            IconButton(
                onClick = onNext,
                enabled = canGoForward,
                modifier = Modifier.size(dimens.iconMedium)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = stringResource(id = R.string.dash_chart_next_cd),
                    tint = if (canGoForward) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

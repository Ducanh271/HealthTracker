package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun SummaryGrid(consumed: Int, burned: Int, balance: Int) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.sm)
    ) {
        SummaryCard(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.dash_consumed),
            value = consumed.toString(),
            color = MaterialTheme.colorScheme.primary
        )
        SummaryCard(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.dash_burned),
            value = burned.toString(),
            color = MaterialTheme.colorScheme.tertiary
        )
        SummaryCard(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.dash_balance),
            value = if (balance > 0) "+$balance" else balance.toString(),
            color = MaterialTheme.colorScheme.secondary,
            isBold = true
        )
    }
}

@Composable
private fun SummaryCard(modifier: Modifier = Modifier, title: String, value: String, color: Color, isBold: Boolean = false) {
    val dimens = LocalDimens.current
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.cornerMedium))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(dimens.borderWidth, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(dimens.cornerMedium))
            .padding(dimens.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(dimens.xs))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal),
            color = if (isBold) color else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryGridPreview() {
    MaterialTheme {
        SummaryGrid(consumed = 1200, burned = 400, balance = 800)
    }
}
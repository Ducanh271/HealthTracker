package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun AdviceBanner(adviceText: String) {
    val dimens = LocalDimens.current
    val primaryColor = MaterialTheme.colorScheme.primary
    val strokeWidthPx = dimens.xs

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            .drawBehind {
                drawLine(
                    color = primaryColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidthPx.toPx()
                )
            }
            .padding(dimens.lg),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info, // Tạm dùng Info thay cho icon bóng đèn
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(dimens.md))
        Column {
            Text(
                text = stringResource(id = R.string.dash_advice_title),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = adviceText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdviceBannerPreview() {
    MaterialTheme {
        AdviceBanner(adviceText = "Bạn đã ăn đủ calo hôm nay! Hãy vận động thêm một chút nhé.")
    }
}
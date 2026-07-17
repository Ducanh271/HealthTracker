package com.example.healthtracker.ui.features.dashboard.dashboardComponents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens
import kotlin.math.abs

@Composable
fun ProgressCircle(consumed: Int, target: Int, remaining: Int) {
    val dimens = LocalDimens.current
    val progress = if (target > 0) (consumed.toFloat() / target.toFloat()).coerceIn(0f, 1f) else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "progress"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    val errorColor = MaterialTheme.colorScheme.error

    Box(
        modifier = Modifier
            .size(250.dp)
            .padding(dimens.md),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx() // thêm strokeThick vào Dimens

            drawArc(
                color = trackColor,
                startAngle = 0f, sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = if (remaining < 0) errorColor else primaryColor,
                startAngle = -90f, sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.dash_target).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$consumed / $target",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            val statusText = if (remaining >= 0) {
                stringResource(id = R.string.dash_remaining, remaining)
            } else {
                stringResource(id = R.string.dash_over, abs(remaining))
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = if (remaining >= 0) MaterialTheme.colorScheme.primary else errorColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressCirclePreview() {
    MaterialTheme {
        ProgressCircle(consumed = 1200, target = 2000, remaining = 800)
    }
}
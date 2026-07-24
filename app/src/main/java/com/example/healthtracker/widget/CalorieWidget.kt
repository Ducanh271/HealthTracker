package com.example.healthtracker.widget

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.healthtracker.MainActivity
import com.example.healthtracker.R
import com.example.healthtracker.di.WidgetEntryPoint
import com.example.healthtracker.domain.model.DashboardMetrics
import com.example.healthtracker.ui.theme.LocalDimens
import com.example.healthtracker.ui.theme.accentColorArgb
import com.example.healthtracker.utils.LocaleUtils
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first
import kotlin.math.abs
import kotlin.math.roundToInt

private const val RING_SIZE_DP = 104
private const val PERCENT_MAX = 100

class CalorieWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        )

        val metrics = entryPoint.getDashboardMetricsUseCase().invoke().first()
        val language = entryPoint.userRepository().appLanguage.first()
        val accentColor = accentColorArgb(entryPoint.userRepository().appTheme.first())
        val localizedContext = LocaleUtils.localizedContext(context, language)

        val progress = progressOf(metrics)
        val percent = (progress * PERCENT_MAX).roundToInt()

        val ring = ProgressRingRenderer.render(
            context = localizedContext,
            progress = progress.coerceAtMost(1f),
            percentLabel = localizedContext.getString(R.string.widget_percent, percent),
            caption = localizedContext.getString(R.string.widget_kcal, metrics.consumedCalories),
            accentColor = accentColor,
            isOverTarget = metrics.remainingCalories < 0
        )

        provideContent {
            GlanceTheme {
                CalorieWidgetContent(localizedContext, metrics, ring, accentColor)
            }
        }
    }

    private fun progressOf(metrics: DashboardMetrics): Float {
        if (metrics.targetCalories <= 0) return 0f
        return (metrics.consumedCalories.toFloat() / metrics.targetCalories).coerceAtLeast(0f)
    }
}

@Composable
private fun CalorieWidgetContent(
    context: Context,
    metrics: DashboardMetrics,
    ring: Bitmap,
    accentColor: Int
) {
    val dimens = LocalDimens.current
    val accent = ColorProvider(Color(accentColor))

    Row(
        modifier = GlanceModifier
            .fillMaxSize()
            .appWidgetBackground()
            .background(GlanceTheme.colors.widgetBackground)
            .cornerRadius(dimens.cornerLarge)
            .padding(dimens.md)
            .clickable(actionStartActivity<MainActivity>()),
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Image(
            provider = ImageProvider(ring),
            contentDescription = context.getString(R.string.widget_name),
            modifier = GlanceModifier.size(RING_SIZE_DP.dp)
        )

        Spacer(GlanceModifier.width(dimens.md))

        Column(modifier = GlanceModifier.defaultWeight()) {
            Text(
                text = context.getString(R.string.widget_name),
                style = TextStyle(
                    color = GlanceTheme.colors.onSurfaceVariant,
                    fontSize = 13.sp
                )
            )

            Spacer(GlanceModifier.height(dimens.sm))

            WidgetStatLine(
                label = context.getString(R.string.widget_target),
                value = context.getString(R.string.widget_kcal, metrics.targetCalories)
            )
            WidgetStatLine(
                label = context.getString(R.string.widget_burned),
                value = context.getString(R.string.widget_kcal, metrics.burnedCalories)
            )

            val remaining = metrics.remainingCalories
            WidgetStatLine(
                label = context.getString(
                    if (remaining >= 0) R.string.widget_remaining else R.string.widget_over
                ),
                value = context.getString(R.string.widget_kcal, abs(remaining)),
                valueColor = accent
            )
        }
    }
}

@Composable
private fun WidgetStatLine(
    label: String,
    value: String,
    valueColor: ColorProvider? = null
) {
    Row(
        modifier = GlanceModifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = GlanceTheme.colors.onSurfaceVariant,
                fontSize = 12.sp
            )
        )
        Spacer(GlanceModifier.defaultWeight())
        Text(
            text = value,
            style = TextStyle(
                color = valueColor ?: GlanceTheme.colors.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

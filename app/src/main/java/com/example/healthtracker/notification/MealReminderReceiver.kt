package com.example.healthtracker.notification

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.healthtracker.MainActivity
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DashboardMetrics
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.dashboard.GetDashboardMetricsUseCase
import com.example.healthtracker.ui.theme.accentColorArgb
import com.example.healthtracker.utils.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class MealReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var getDashboardMetricsUseCase: GetDashboardMetricsUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val slot = ReminderSlot.fromName(intent.getStringExtra(EXTRA_SLOT)) ?: return

        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                if (!userRepository.notificationsEnabled.first()) return@launch

                val language = userRepository.appLanguage.first()
                val accentColor = accentColorArgb(userRepository.appTheme.first())
                val metrics = getDashboardMetricsUseCase().first()

                showReminder(
                    context = LocaleUtils.localizedContext(context, language),
                    slot = slot,
                    metrics = metrics,
                    accentColor = accentColor
                )
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun showReminder(
        context: Context,
        slot: ReminderSlot,
        metrics: DashboardMetrics,
        accentColor: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val contentIntent = PendingIntent.getActivity(
            context,
            slot.requestCode,
            Intent(context, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val summary = buildSummary(context, metrics)

        val notification = NotificationCompat.Builder(context, ReminderScheduler.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_meal)
            .setColor(accentColor)
            .setContentTitle(context.getString(slot.titleRes))
            .setContentText(summary)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("${context.getString(slot.textRes)}\n$summary")
            )
            .setProgress(PROGRESS_MAX, progressPercent(metrics), false)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .addAction(
                R.drawable.ic_notification_meal,
                context.getString(R.string.reminder_action_open),
                contentIntent
            )
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(slot.notificationId, notification)
    }

    private fun buildSummary(context: Context, metrics: DashboardMetrics): String {
        val progress = context.getString(
            R.string.reminder_progress_today,
            metrics.consumedCalories,
            metrics.targetCalories
        )

        val remaining = metrics.remainingCalories
        val remainingText = if (remaining >= 0) {
            context.getString(R.string.reminder_remaining, remaining)
        } else {
            context.getString(R.string.reminder_over, abs(remaining))
        }

        return "$progress · $remainingText"
    }

    private fun progressPercent(metrics: DashboardMetrics): Int {
        if (metrics.targetCalories <= 0) return 0
        val ratio = metrics.consumedCalories.toFloat() / metrics.targetCalories
        return (ratio * PROGRESS_MAX).toInt().coerceIn(0, PROGRESS_MAX)
    }

    companion object {
        const val EXTRA_SLOT = "extra_reminder_slot"
        private const val PROGRESS_MAX = 100
    }
}

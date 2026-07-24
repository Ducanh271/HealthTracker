package com.example.healthtracker.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.healthtracker.R
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.utils.LocaleUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) {

    suspend fun refreshDailyReminders() {
        createChannel()

        if (userRepository.notificationsEnabled.first()) {
            ReminderSlot.entries.forEach { scheduleSlot(it) }
        } else {
            ReminderSlot.entries.forEach { cancelSlot(it) }
        }
    }

    suspend fun scheduleTestReminder() {
        createChannel()

        val intent = reminderIntent(currentSlot())
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            TEST_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager().set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + TEST_DELAY_MILLIS,
            pendingIntent
        )
    }

    private suspend fun createChannel() {
        val language = userRepository.appLanguage.first()
        val localizedContext = LocaleUtils.localizedContext(context, language)

        val channel = NotificationChannel(
            CHANNEL_ID,
            localizedContext.getString(R.string.reminder_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = localizedContext.getString(R.string.reminder_channel_desc)
        }
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    private fun scheduleSlot(slot: ReminderSlot) {
        alarmManager().setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            nextTriggerAtHour(slot.hourOfDay),
            AlarmManager.INTERVAL_DAY,
            slotPendingIntent(slot, PendingIntent.FLAG_UPDATE_CURRENT)
        )
    }

    private fun cancelSlot(slot: ReminderSlot) {
        alarmManager().cancel(slotPendingIntent(slot, PendingIntent.FLAG_UPDATE_CURRENT))
    }

    private fun slotPendingIntent(slot: ReminderSlot, flag: Int): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            slot.requestCode,
            reminderIntent(slot),
            flag or PendingIntent.FLAG_IMMUTABLE
        )

    private fun reminderIntent(slot: ReminderSlot): Intent =
        Intent(context, MealReminderReceiver::class.java).apply {
            putExtra(MealReminderReceiver.EXTRA_SLOT, slot.name)
        }

    private fun alarmManager(): AlarmManager =
        context.getSystemService(AlarmManager::class.java)

    private fun currentSlot(): ReminderSlot {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return ReminderSlot.entries.lastOrNull { hour >= it.hourOfDay } ?: ReminderSlot.BREAKFAST
    }

    private fun nextTriggerAtHour(hourOfDay: Int): Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hourOfDay)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }.timeInMillis

    companion object {
        const val CHANNEL_ID = "meal_reminder_channel"
        private const val TEST_REQUEST_CODE = 2999
        private const val TEST_DELAY_MILLIS = 10_000L
    }
}

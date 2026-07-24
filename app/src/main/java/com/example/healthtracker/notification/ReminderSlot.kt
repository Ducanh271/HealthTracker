package com.example.healthtracker.notification

import androidx.annotation.StringRes
import com.example.healthtracker.R

enum class ReminderSlot(
    val hourOfDay: Int,
    @StringRes val titleRes: Int,
    @StringRes val textRes: Int
) {
    BREAKFAST(7, R.string.reminder_breakfast_title, R.string.reminder_breakfast_text),
    LUNCH(12, R.string.reminder_lunch_title, R.string.reminder_lunch_text),
    DINNER(19, R.string.reminder_dinner_title, R.string.reminder_dinner_text);

    val requestCode: Int get() = REQUEST_CODE_BASE + hourOfDay

    val notificationId: Int get() = NOTIFICATION_ID_BASE + hourOfDay

    companion object {
        private const val REQUEST_CODE_BASE = 2000
        private const val NOTIFICATION_ID_BASE = 1000

        fun fromName(name: String?): ReminderSlot? = entries.firstOrNull { it.name == name }
    }
}

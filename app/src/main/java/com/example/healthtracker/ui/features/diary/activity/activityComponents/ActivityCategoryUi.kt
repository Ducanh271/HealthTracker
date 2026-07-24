package com.example.healthtracker.ui.features.diary.activity.activityComponents

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityCategory

fun ActivityCategory.icon(): ImageVector = when (this) {
    ActivityCategory.RUNNING -> Icons.Default.DirectionsRun
    ActivityCategory.CYCLING -> Icons.Default.PedalBike
    ActivityCategory.SWIMMING -> Icons.Default.Pool
    ActivityCategory.STRENGTH -> Icons.Default.FitnessCenter
    ActivityCategory.YOGA -> Icons.Default.SelfImprovement
    ActivityCategory.OTHER -> Icons.Default.DirectionsRun
}

@StringRes
fun ActivityCategory.labelRes(): Int = when (this) {
    ActivityCategory.RUNNING -> R.string.activity_category_cardio
    ActivityCategory.CYCLING -> R.string.activity_category_cardio
    ActivityCategory.SWIMMING -> R.string.activity_category_sports
    ActivityCategory.STRENGTH -> R.string.activity_category_strength
    ActivityCategory.YOGA -> R.string.activity_category_yoga
    ActivityCategory.OTHER -> R.string.activity_category_other
}

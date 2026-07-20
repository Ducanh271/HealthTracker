package com.example.healthtracker.domain.usecase.dashboard

import android.content.ContentValues.TAG
import android.util.Log
import javax.inject.Inject
import kotlin.math.roundToInt


class CalculateTdeeUseCase @Inject constructor() {
    operator fun invoke(
        gender: String,
        weightKg: Float,
        heightCm: Float,
        age: Int,
        activityLevel: Int,
        goal: String
    ): Int{
        val bmr = if (gender.equals("Nam", ignoreCase = true)) {
            (10 * weightKg) + (6.25f * heightCm) - (5 * age) + 5
        } else {
            (10 * weightKg) + (6.25f * heightCm) - (5 * age) - 161
        }
        val activityMultiplier = when (activityLevel) {
            1 -> 1.2f
            2 -> 1.375f
            3 -> 1.55f
            4 -> 1.725f
            5 -> 1.9f
            else -> 1.2f
        }

        val tdeeBase = bmr * activityMultiplier

        val finalTdee = when (goal) {
            "Giảm cân" -> tdeeBase - 500
            "Tăng cân" -> tdeeBase + 500
            "Giữ cân" -> tdeeBase
            else -> tdeeBase
        }
        Log.d(TAG, "Input: gender=$gender, weight=$weightKg, height=$heightCm, age=$age, level=$activityLevel, goal=$goal")
        Log.d(TAG, "Calculation: BMR=$bmr, Multiplier=$activityMultiplier, TDEE_Base=$tdeeBase, Final_TDEE=${finalTdee.roundToInt()}")
        return finalTdee.roundToInt()
    }
}
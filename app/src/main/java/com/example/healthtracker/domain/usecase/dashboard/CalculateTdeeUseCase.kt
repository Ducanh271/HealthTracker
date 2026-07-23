package com.example.healthtracker.domain.usecase.dashboard

import android.content.ContentValues.TAG
import android.util.Log
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import javax.inject.Inject
import kotlin.math.roundToInt


class CalculateTdeeUseCase @Inject constructor() {
    operator fun invoke(
        gender: Gender,
        weightKg: Float,
        heightCm: Float,
        age: Int,
        activityLevel: Int,
        goal: Goal
    ): Int{
        val bmr = if (gender == Gender.MALE) {
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
            Goal.LOSE_WEIGHT -> tdeeBase - 500
            Goal.GAIN_WEIGHT -> tdeeBase + 500
            Goal.MAINTAIN_WEIGHT -> tdeeBase
            else -> tdeeBase
        }
        Log.d(TAG, "Input: gender=$gender, weight=$weightKg, height=$heightCm, age=$age, level=$activityLevel, goal=$goal")
        Log.d(TAG, "Calculation: BMR=$bmr, Multiplier=$activityMultiplier, TDEE_Base=$tdeeBase, Final_TDEE=${finalTdee.roundToInt()}")
        return finalTdee.roundToInt()
    }
}
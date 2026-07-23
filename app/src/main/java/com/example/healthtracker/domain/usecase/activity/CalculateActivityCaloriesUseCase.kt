package com.example.healthtracker.domain.usecase.activity

import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateActivityCaloriesUseCase @Inject constructor() {
    operator fun invoke(metValue: Float, weightKg: Float, durationMinutes: Int): Int {
        return (metValue * weightKg * (durationMinutes / 60f)).roundToInt()
    }
}
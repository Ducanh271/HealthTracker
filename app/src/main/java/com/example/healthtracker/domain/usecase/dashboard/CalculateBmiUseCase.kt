package com.example.healthtracker.domain.usecase.dashboard

import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

data class BmiResult(
    val bmi: Float,
    val category: String
)

class CalculateBmiUseCase @Inject constructor() {
    operator fun invoke(weightKg: Float, height: Float): BmiResult{
        if(height <= 0f) return BmiResult(0f, "Không xác định")
        val heightM = height/100
        val bmi = weightKg/(heightM.pow(2))

        val roundedBmi = (bmi * 10 ).roundToInt() /10f
        val category = when {
            roundedBmi < 18.5f -> "Thiếu cân"
            roundedBmi <25f -> "Bình thường"
            roundedBmi < 30f -> "Thừa cân"
            else -> "Béo phì"
        }
        return BmiResult(roundedBmi, category)
    }
}
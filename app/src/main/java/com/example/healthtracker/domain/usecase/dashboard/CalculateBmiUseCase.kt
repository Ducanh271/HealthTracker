package com.example.healthtracker.domain.usecase.dashboard

import com.example.healthtracker.domain.model.BmiCategory
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

data class BmiResult(
    val bmi: Float,
    val category: BmiCategory
)

class CalculateBmiUseCase @Inject constructor() {
    operator fun invoke(weightKg: Float, height: Float): BmiResult{
        if(height <= 0f) return BmiResult(0f, BmiCategory.UNDEFINED)
        val heightM = height/100
        val bmi = weightKg/(heightM.pow(2))

        val roundedBmi = (bmi * 10 ).roundToInt() /10f
        val category = when {
            roundedBmi < 18.5f -> BmiCategory.UNDERWEIGHT
            roundedBmi <25f -> BmiCategory.NORMAL
            roundedBmi < 30f -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }
        return BmiResult(roundedBmi, category)
    }
}
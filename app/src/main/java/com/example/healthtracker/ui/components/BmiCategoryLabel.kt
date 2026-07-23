package com.example.healthtracker.ui.components

import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory

fun BmiCategory.labelRes(): Int = when (this) {
    BmiCategory.UNDERWEIGHT -> R.string.bmi_underweight
    BmiCategory.NORMAL -> R.string.bmi_normal
    BmiCategory.OVERWEIGHT -> R.string.bmi_overweight
    BmiCategory.OBESE -> R.string.bmi_obese
    BmiCategory.UNDEFINED -> R.string.bmi_undefined
}

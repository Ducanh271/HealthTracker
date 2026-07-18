package com.example.healthtracker.domain.usecase

import com.example.healthtracker.data.local.room.entity.MealLogEntity
import com.example.healthtracker.domain.repository.MealRepository
import javax.inject.Inject

class AddMealLogUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(date: String, type: String, name: String, serving: Int, calories: Int) {
        val newMeal = MealLogEntity(
            date = date,
            mealType = type,
            foodName = name,
            servingCount = serving,
            totalCalories = calories
        )
        mealRepository.insertMealLog(newMeal)
    }
}
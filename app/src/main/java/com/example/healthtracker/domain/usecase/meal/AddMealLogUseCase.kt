package com.example.healthtracker.domain.usecase.meal

import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.repository.MealRepository
import javax.inject.Inject

class AddMealLogUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(date: String, type: String, name: String, serving: Int, calories: Int) {
        val newMeal = Meal(
            date = date,
            mealType = type,
            foodName = name,
            servingCount = serving,
            totalCalories = calories
        )
        mealRepository.insertMealLog(newMeal)
    }
}

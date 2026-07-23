package com.example.healthtracker.domain.usecase.meal

import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.repository.MealRepository
import javax.inject.Inject

class AddNewFoodItemUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(name: String, servingSize: String, calories: Int): FoodItem {
        val newItem = FoodItem(
            name = name,
            servingSize = servingSize,
            calories = calories
        )
        val generatedId = mealRepository.insertFoodItem(newItem)
        return newItem.copy(id = generatedId.toInt())
    }
}

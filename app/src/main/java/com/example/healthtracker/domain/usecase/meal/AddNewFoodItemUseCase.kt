package com.example.healthtracker.domain.usecase.meal

import android.util.Log
import com.example.healthtracker.data.local.room.entity.FoodItemEntity
import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.repository.MealRepository
import javax.inject.Inject

class AddNewFoodItemUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(name: String, servingSize: String, calories: Int): FoodItem {
        val newEntity = FoodItemEntity(
            name = name,
            servingSize = servingSize,
            calories = calories
        )

        val generatedId = mealRepository.insertFoodItem(newEntity)

        Log.d("HealthTracker","id: $generatedId, name: $name, servingSize: $servingSize, calories: $calories")
        return FoodItem(
            id = generatedId.toInt(),
            name = name,
            servingSize = servingSize,
            calories = calories
        )
    }
}
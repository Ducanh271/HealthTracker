package com.example.healthtracker.domain.usecase

import com.example.healthtracker.data.mapper.toEntity
import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.repository.MealRepository
import javax.inject.Inject

class DeleteMealLogUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(meal: Meal) {
        mealRepository.deleteMealLog(meal.toEntity())
    }
}
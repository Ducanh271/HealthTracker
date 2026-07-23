package com.example.healthtracker.domain.usecase.meal

import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodItemsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(query: String): Flow<List<FoodItem>> {
        return mealRepository.searchFoodItems(query)
    }
}

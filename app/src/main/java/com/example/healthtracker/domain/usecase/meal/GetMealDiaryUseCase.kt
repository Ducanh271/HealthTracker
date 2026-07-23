package com.example.healthtracker.domain.usecase.meal

import com.example.healthtracker.data.mapper.toDomain
import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.repository.MealRepository
import com.example.healthtracker.domain.repository.UserRepository // Sử dụng UserRepository thay cho DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class MealDiaryData(
    val targetCalories: Int = 2000,
    val totalCalories: Int = 0,
    val breakfast: List<Meal> = emptyList(),
    val lunch: List<Meal> = emptyList(),
    val dinner: List<Meal> = emptyList(),
    val snacks: List<Meal> = emptyList()
)

class GetMealDiaryUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(dateStr: String): Flow<MealDiaryData> {
        return combine(
            userRepository.userProfile,
            mealRepository.getMealLogsByDate(dateStr)
        ) { profile, entityList ->

            val mealList = entityList.map { it.toDomain() }

            MealDiaryData(
                targetCalories = profile.tdee,
                totalCalories = mealList.sumOf { it.totalCalories },
                breakfast = mealList.filter { it.mealType == "Bữa sáng" },
                lunch = mealList.filter { it.mealType == "Bữa trưa" },
                dinner = mealList.filter { it.mealType == "Bữa tối" },
                snacks = mealList.filter { it.mealType == "Bữa phụ" }
            )
        }
    }
}
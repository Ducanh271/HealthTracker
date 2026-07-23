package com.example.healthtracker.domain.usecase.meal

import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.domain.repository.MealRepository
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class MealDiaryData(
    val targetCalories: Int = UserProfile.DEFAULT_TDEE,
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
        ) { profile, mealList ->
            MealDiaryData(
                targetCalories = profile.tdee,
                totalCalories = mealList.sumOf { it.totalCalories },
                breakfast = mealList.filter { it.mealType == MealType.BREAKFAST },
                lunch = mealList.filter { it.mealType == MealType.LUNCH },
                dinner = mealList.filter { it.mealType == MealType.DINNER },
                snacks = mealList.filter { it.mealType == MealType.SNACK }
            )
        }
    }
}

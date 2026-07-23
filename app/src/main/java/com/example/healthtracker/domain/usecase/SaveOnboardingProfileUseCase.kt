package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.dashboard.CalculateTdeeUseCase
import com.example.healthtracker.utils.DateUtils
import javax.inject.Inject

class SaveOnboardingProfileUseCase @Inject constructor(
    private val calculateTdeeUseCase: CalculateTdeeUseCase,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        dob: String,
        gender: Gender,
        weight: String,
        height: String,
        activityLevel: Int,
        goal: Goal
    ): Result<Unit> = try {
        val weightKg = weight.toFloatOrNull() ?: 0f
        val heightCm = height.toFloatOrNull() ?: 0f
        val calculatedAge = DateUtils.calculateAgeOrNull(dob) ?: 20

        val tdee = calculateTdeeUseCase(
            gender = gender,
            weightKg = weightKg,
            heightCm = heightCm,
            age = calculatedAge,
            activityLevel = activityLevel,
            goal = goal
        )

        userRepository.saveUserProfile(
            name = name,
            age = calculatedAge,
            gender = gender.name,
            weight = weightKg,
            height = heightCm,
            activityLevel = activityLevel,
            goal = goal.name,
            tdee = tdee
        )
        userRepository.saveOnboardingStatus(true)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
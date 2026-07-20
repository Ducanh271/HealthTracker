package com.example.healthtracker.domain.usecase

import com.example.healthtracker.data.local.datastore.SettingsDataStore
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.ui.features.onboarding.OnboardingState
import com.example.healthtracker.utils.DateUtils
import javax.inject.Inject

class SaveOnboardingProfileUseCase @Inject constructor(
    private val calculateTdeeUseCase: CalculateTdeeUseCase,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(currentState: OnboardingState) {
        val weightKg = currentState.weight.toFloat()
        val heightCm = currentState.height.toFloat()
        val calculatedAge = DateUtils.calculateAgeOrNull(currentState.dob) ?: 20
        val genderStr = if (currentState.isMale) OnboardingState.GENDER_MALE else OnboardingState.GENDER_FEMALE

        val tdee = calculateTdeeUseCase(
            gender = genderStr,
            weightKg = weightKg,
            heightCm = heightCm,
            age = calculatedAge,
            activityLevel = currentState.activityLevel,
            goal = currentState.goal
        )

        userRepository.saveUserProfile(
            name = currentState.name,
            age = calculatedAge,
            gender = genderStr,
            weight = weightKg,
            height = heightCm,
            activityLevel = currentState.activityLevel,
            goal = currentState.goal,
            tdee = tdee
        )
        userRepository.saveOnboardingStatus(true)
    }
}
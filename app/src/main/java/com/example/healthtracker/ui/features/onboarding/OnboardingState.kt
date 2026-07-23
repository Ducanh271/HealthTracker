package com.example.healthtracker.ui.features.onboarding

import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal

data class OnboardingState(
    val name: String = "",
    val dob: String = "",
    val gender: Gender = Gender.MALE,
    val weight: String = "",
    val height: String = "",
    val activityLevel: Int = 3,
    val goal: Goal = Goal.MAINTAIN_WEIGHT,
    val weightError: Int? = null,
    val heightError: Int? = null,
    val nameError: Int? = null,
    val dobError: Int? = null,
    val isLoading: Boolean = false,
    val isNavigateToDashboard: Boolean = false
)

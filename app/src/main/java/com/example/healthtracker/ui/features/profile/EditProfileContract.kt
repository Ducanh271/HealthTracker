package com.example.healthtracker.ui.features.profile

import com.example.healthtracker.domain.model.BmiCategory

data class EditProfileState(
    val name: String = "",
    val age: String = "25",
    val isMale: Boolean = true,
    val weight: String = "",
    val height: String = "",
    val activityLevel: Float = 3f,
    val goal: String = "Giữ cân",

    val bmiValue: Float = 0f,
    val bmiCategory: BmiCategory = BmiCategory.UNDEFINED,
    val tdeeValue: Int = 0,

    val isLoading: Boolean = false,
    val showSuccessToast: Boolean = false,
    val weightError: Boolean = false,
    val heightError: Boolean = false
)

sealed class EditProfileEvent {
    data class OnNameChange(val name: String) : EditProfileEvent()
    data class OnAgeChange(val age: String) : EditProfileEvent()
    data class OnGenderChange(val isMale: Boolean) : EditProfileEvent()
    data class OnWeightChange(val weight: String) : EditProfileEvent()
    data class OnHeightChange(val height: String) : EditProfileEvent()
    data class OnActivityLevelChange(val level: Float) : EditProfileEvent()
    data class OnGoalChange(val goal: String) : EditProfileEvent()
    object OnSaveClick : EditProfileEvent()
    object OnToastDismiss : EditProfileEvent()
}
package com.example.healthtracker.ui.features.onboarding

data class OnboardingState(
    val name: String = "",
    val dob: String = "",
    val isMale: Boolean = true,
    val weight: String = "",
    val height: String = "",
    val activityLevel: Int = 3,
    val goal: String = GOAL_MAINTAIN,
    val weightError: Int? = null,
    val heightError: Int? = null,
    val nameError: Int? = null,
    val dobError: Int? = null,
    val isLoading: Boolean = false,
    val isNavigateToDashboard: Boolean = false
) {
    companion object {
        const val GOAL_LOSE = "lose"
        const val GOAL_MAINTAIN = "maintain"
        const val GOAL_GAIN = "gain"
        const val GENDER_MALE = "Nam"
        const val GENDER_FEMALE = "Nữ"
    }
}

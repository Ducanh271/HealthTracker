package com.example.healthtracker.ui.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.healthtracker.domain.usecase.SaveOnboardingProfileUseCase
import com.example.healthtracker.utils.DateUtils

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingProfileUseCase: SaveOnboardingProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun updateName(newName: String) { _state.update { it.copy(name = newName) } }
    fun updateDob(newDob: String) { _state.update { it.copy(dob = newDob) } }
    fun updateGender(newGender: Gender) { _state.update { it.copy(gender = newGender) } }
    fun updateWeight(newWeight: String) { _state.update { it.copy(weight = newWeight) } }
    fun updateHeight(newHeight: String) { _state.update { it.copy(height = newHeight) } }
    fun updateActivityLevel(level: Int) { _state.update { it.copy(activityLevel = level) } }

    fun updateGoal(newGoal: Goal) { _state.update { it.copy(goal = newGoal) } }

    fun completeOnboarding() {
        val currentState = _state.value

        if (currentState.isLoading) return
        if (!validateInputs(currentState)) return

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val result = saveOnboardingProfileUseCase(
                    name = currentState.name,
                    dob = currentState.dob,
                    gender = currentState.gender,
                    weight = currentState.weight,
                    height = currentState.height,
                    activityLevel = currentState.activityLevel,
                    goal = currentState.goal
                )

                if (result.isSuccess) {
                    _state.update { it.copy(isNavigateToDashboard = true) }
                }
            } catch(e: Exception) {
                android.util.Log.e("OnboardingBug", "Lỗi: ${e.message}", e)
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateInputs(currentState: OnboardingState): Boolean {
        var hasError = false
        var nError: Int? = null
        var dError: Int? = null
        var wError: Int? = null
        var hError: Int? = null

        if (currentState.name.trim().isEmpty()) {
            nError = R.string.error_name_empty
            hasError = true
        }

        val age = DateUtils.calculateAgeOrNull(currentState.dob)
        if (age == null || age < 1) {
            dError = R.string.error_dob_invalid
            hasError = true
        }

        val weightKg = currentState.weight.toFloatOrNull() ?: 0f
        if (weightKg !in 5f..300f) {
            wError = R.string.error_weight_invalid
            hasError = true
        }

        val heightCm = currentState.height.toFloatOrNull() ?: 0f
        if (heightCm !in 50f..300f) {
            hError = R.string.error_height_invalid
            hasError = true
        }

        _state.update {
            it.copy(
                nameError = nError,
                dobError = dError,
                weightError = wError,
                heightError = hError
            )
        }

        return !hasError
    }

    fun resetNavigationFlag() {
        _state.update { it.copy(isNavigateToDashboard = false) }
    }
}
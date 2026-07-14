package com.example.healthtracker.ui.features.onboarding

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.newStringBuilder
import com.example.healthtracker.R
import com.example.healthtracker.data.local.datastore.SettingsDataStore
import com.example.healthtracker.domain.usecase.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.CalculateTdeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class OnboardingState(
    val name: String = "",
    val dob: String = "",
    val isMale: Boolean = true,
    val weight: String = "",
    val height: String = "",
    val activityLevel: Int = 3,
    val goal: String = GOAL_MAINTAIN,
    val weightError: String? = null,
    val heightError: String? = null,
    val isLoading: Boolean = false
) {
    companion object {
        const val GOAL_LOSE = "lose"
        const val GOAL_MAINTAIN = "maintain"
        const val GOAL_GAIN = "gain"
        const val GENDER_MALE = "Nam"
        const val GENDER_FEMALE = "Nữ"
    }
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val calculateTdeeUseCase: CalculateTdeeUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun updateName(newName: String) {
        _state.update { it.copy(name = newName) }
    }

    fun updateDob(newDob: String) {
        _state.update { it.copy(dob = newDob) }
    }

    fun updateGender(isMale: Boolean) {
        _state.update { it.copy(isMale = isMale) }
    }

    fun updateWeight(newWeight: String) {
        _state.update { it.copy(weight = newWeight) }
    }

    fun updateHeight(newHeight: String) {
        _state.update { it.copy(height = newHeight) }
    }

    fun updateActivityLevel(level: Int) {
        _state.update { it.copy(activityLevel = level) }
    }

    fun updateGoal(newGoal: String) {
        _state.update { it.copy(goal = newGoal) }
    }

    fun completeOnboarding(onSuccess: () -> Unit) {
        val currentState = _state.value

        if (currentState.isLoading) return

        val weightKg = currentState.weight.toFloatOrNull() ?: 0f
        val heightCm = currentState.height.toFloatOrNull() ?: 0f

        var hasError = false
        var wError: String? = null
        var hError: String? = null

        if (weightKg !in 5f..300f) {
            wError = R.string.error_weight_invalid.toString()
            hasError = true
        }

        if (heightCm !in 50f..300f) {
            hError = R.string.error_height_invalid.toString()
            hasError = true
        }

        if (hasError) {
            _state.update { it.copy(weightError = wError, heightError = hError) }
            return
        }

        _state.update { it.copy(weightError = null, heightError = null) }

        val calculatedAge = calculateAge(currentState.dob)
        val genderStr =
            if (currentState.isMale) OnboardingState.GENDER_MALE else OnboardingState.GENDER_FEMALE

        val tdee = calculateTdeeUseCase(
            gender = genderStr,
            weightKg = weightKg,
            heightCm = heightCm,
            age = calculatedAge,
            activityLevel = currentState.activityLevel,
            goal = currentState.goal
        )

        viewModelScope.launch {
            try {
                settingsDataStore.saveUserProfile(
                    name = currentState.name,
                    age = calculatedAge,
                    gender = genderStr,
                    weight = weightKg,
                    height = heightCm,
                    activityLevel = currentState.activityLevel,
                    goal = currentState.goal,
                    tdee = tdee
                )
                settingsDataStore.saveOnboardingStatus(true)
                onSuccess()
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    // func tinh tuoi
    private fun calculateAge(dobStr: String): Int {
        return try {
            val formatter = if (dobStr.contains("/")) {
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            } else {
                DateTimeFormatter.ISO_LOCAL_DATE
            }
            val birthDate = LocalDate.parse(dobStr, formatter)
            val currentDate = LocalDate.now()

            var age = currentDate.year - birthDate.year
            if (currentDate.dayOfYear < birthDate.dayOfYear) {
                age--
            }

            if (age > 0) age else 0
        } catch (e: Exception) {
            // Nếu parse lỗi, trả về giá trị mặc định (Ví dụ: 20) để công thức TDEE không bị hỏng
            20
        }
    }
}



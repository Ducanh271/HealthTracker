package com.example.healthtracker.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.usecase.dashboard.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.dashboard.CalculateTdeeUseCase
import com.example.healthtracker.domain.usecase.profile.GetUserProfileUseCase
import com.example.healthtracker.domain.usecase.profile.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val calculateTdeeUseCase: CalculateTdeeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val profile = getUserProfileUseCase().first()

            val genderEnum = runCatching { Gender.valueOf(profile.gender) }.getOrDefault(Gender.MALE)
            val goalEnum = runCatching { Goal.valueOf(profile.goal) }.getOrDefault(Goal.MAINTAIN_WEIGHT)

            _state.value = _state.value.copy(
                name = profile.name,
                age = profile.age.toString(),
                gender = genderEnum,
                weight = profile.weight.toString(),
                height = profile.height.toString(),
                activityLevel = profile.activityLevel.toFloat(),
                goal = goalEnum
            )
            recalculateMetrics()
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.OnNameChange -> { _state.value = _state.value.copy(name = event.name) }
            is EditProfileEvent.OnAgeChange -> { _state.value = _state.value.copy(age = event.age) }
            is EditProfileEvent.OnGenderChange -> {
                _state.value = _state.value.copy(gender = event.gender)
                recalculateMetrics()
            }
            is EditProfileEvent.OnWeightChange -> {
                val w = event.weight.toFloatOrNull()
                _state.value = _state.value.copy(weight = event.weight, weightError = w == null || w < 5f || w > 300f)
                recalculateMetrics()
            }
            is EditProfileEvent.OnHeightChange -> {
                val h = event.height.toFloatOrNull()
                _state.value = _state.value.copy(height = event.height, heightError = h == null || h < 50f || h > 300f)
                recalculateMetrics()
            }
            is EditProfileEvent.OnActivityLevelChange -> {
                _state.value = _state.value.copy(activityLevel = event.level)
                recalculateMetrics()
            }
            is EditProfileEvent.OnGoalChange -> {
                _state.value = _state.value.copy(goal = event.goal)
                recalculateMetrics()
            }
            is EditProfileEvent.OnSaveClick -> saveProfile()
            is EditProfileEvent.OnToastDismiss -> { _state.value = _state.value.copy(showSuccessToast = false) }
        }
    }

    private fun recalculateMetrics() {
        val w = _state.value.weight.toFloatOrNull() ?: 0f
        val h = _state.value.height.toFloatOrNull() ?: 0f
        val a = _state.value.age.toIntOrNull() ?: 20

        val bmiResult = calculateBmiUseCase(w, h)

        val tdee = calculateTdeeUseCase(
            gender = _state.value.gender,
            weightKg = w,
            heightCm = h,
            age = a,
            activityLevel = _state.value.activityLevel.toInt(),
            goal = _state.value.goal
        )

        _state.value = _state.value.copy(
            bmiValue = bmiResult.bmi,
            bmiCategory = bmiResult.category,
            tdeeValue = tdee
        )
    }

    private fun saveProfile() {
        if (_state.value.weightError || _state.value.heightError) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            delay(1000)

            saveUserProfileUseCase(
                name = _state.value.name,
                age = _state.value.age.toIntOrNull() ?: 20,
                gender = _state.value.gender,
                weight = _state.value.weight.toFloatOrNull() ?: 0f,
                height = _state.value.height.toFloatOrNull() ?: 0f,
                activityLevel = _state.value.activityLevel.toInt(),
                goal = _state.value.goal,
                tdee = _state.value.tdeeValue
            )

            _state.value = _state.value.copy(isLoading = false, showSuccessToast = true)
        }
    }
}
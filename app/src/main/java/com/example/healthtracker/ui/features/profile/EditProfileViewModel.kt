package com.example.healthtracker.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.dashboard.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.dashboard.CalculateTdeeUseCase
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
    private val userRepository: UserRepository,
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
            val profile = userRepository.userProfile.first()

            val genderEnum = when (profile.gender) {
                "Nữ", Gender.FEMALE.name -> Gender.FEMALE
                else -> Gender.MALE
            }

            val goalEnum = when (profile.goal) {
                "Giảm cân", "Lose Weight", Goal.LOSE_WEIGHT.name -> Goal.LOSE_WEIGHT
                "Tăng cân", "Gain Weight", Goal.GAIN_WEIGHT.name -> Goal.GAIN_WEIGHT
                else -> Goal.MAINTAIN_WEIGHT
            }

            _state.value = _state.value.copy(
                name = profile.name,
                age = profile.age.toString(),
                gender = genderEnum, // Đã map sang Enum
                weight = profile.weight.toString(),
                height = profile.height.toString(),
                activityLevel = profile.activityLevel.toFloat(),
                goal = goalEnum // Đã map sang Enum
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

            userRepository.saveUserProfile(
                name = _state.value.name,
                age = _state.value.age.toIntOrNull() ?: 20,
                gender = _state.value.gender.name,
                weight = _state.value.weight.toFloatOrNull() ?: 0f,
                height = _state.value.height.toFloatOrNull() ?: 0f,
                activityLevel = _state.value.activityLevel.toInt(),
                goal = _state.value.goal.name,
                tdee = _state.value.tdeeValue
            )

            _state.value = _state.value.copy(isLoading = false, showSuccessToast = true)
        }
    }
}
package com.example.healthtracker.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            _state.value = _state.value.copy(
                name = profile.name,
                age = profile.age.toString(),
                isMale = profile.gender.equals("Nam", ignoreCase = true),
                weight = profile.weight.toString(),
                height = profile.height.toString(),
                activityLevel = profile.activityLevel.toFloat(),
                goal = profile.goal
            )
            recalculateMetrics()
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.OnNameChange -> { _state.value = _state.value.copy(name = event.name) }
            is EditProfileEvent.OnAgeChange -> { _state.value = _state.value.copy(age = event.age) }
            is EditProfileEvent.OnGenderChange -> {
                _state.value = _state.value.copy(isMale = event.isMale)
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
        val g = if (_state.value.isMale) "Nam" else "Nữ"

        val bmiResult = calculateBmiUseCase(w, h)
        val tdee = calculateTdeeUseCase(g, w, h, a, _state.value.activityLevel.toInt(), _state.value.goal)

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

            // Giả lập delay API/Save như HTML yêu cầu
            delay(1000)

            userRepository.saveUserProfile(
                name = _state.value.name,
                age = _state.value.age.toIntOrNull() ?: 20,
                gender = if (_state.value.isMale) "Nam" else "Nữ",
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
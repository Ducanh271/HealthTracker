package com.example.healthtracker.ui.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import android.util.Log
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

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val calculateTdeeUseCase: CalculateTdeeUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun updateName(newName: String) { _state.update { it.copy(name = newName) } }
    fun updateDob(newDob: String) { _state.update { it.copy(dob = newDob) } }
    fun updateGender(isMale: Boolean) { _state.update { it.copy(isMale = isMale) } }
    fun updateWeight(newWeight: String) { _state.update { it.copy(weight = newWeight) } }
    fun updateHeight(newHeight: String) { _state.update { it.copy(height = newHeight) } }
    fun updateActivityLevel(level: Int) { _state.update { it.copy(activityLevel = level) } }
    fun updateGoal(newGoal: String) { _state.update { it.copy(goal = newGoal) } }

    fun completeOnboarding() {
        Log.d("OnboardingFlow", "TRẠM 1: Nút bấm đã kích hoạt hàm completeOnboarding")
        val currentState = _state.value

        if (currentState.isLoading) {
            Log.d("OnboardingFlow", "-> BỊ CHẶN: Đang loading, không cho bấm double")
            return
        }

        if (!validateInputs(currentState)) {
            Log.d("OnboardingFlow", "-> BỊ CHẶN: Validate thất bại! (Tên: '${currentState.name}', Ngày sinh: '${currentState.dob}', Nặng: ${currentState.weight}, Cao: ${currentState.height})")
            return
        }
        Log.d("OnboardingFlow", "TRẠM 2: Dữ liệu hợp lệ, bắt đầu tính TDEE")

        _state.update { it.copy(isLoading = true) }
        val weightKg = currentState.weight.toFloat()
        val heightCm = currentState.height.toFloat()
        val calculatedAge = calculateAgeOrNull(currentState.dob) ?: 20
        val genderStr = if (currentState.isMale) OnboardingState.GENDER_MALE else OnboardingState.GENDER_FEMALE

        val tdee = calculateTdeeUseCase(
            gender = genderStr,
            weightKg = weightKg,
            heightCm = heightCm,
            age = calculatedAge,
            activityLevel = currentState.activityLevel,
            goal = currentState.goal
        )
        Log.d("OnboardingFlow", "TRẠM 3: Tính xong TDEE = $tdee. Đang lưu DataStore...")
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
                Log.d("OnboardingFlow", "TRẠM 4: Lưu DataStore thành công! Đang gọi hàm chuyển trang...")
                _state.update { it.copy(isNavigateToDashboard = true) }
            }catch(e: Exception){
                Log.e("OnboardingBug", "Lỗi khi tính toán hoặc lưu DataStore: ${e.message}", e)
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

        val age = calculateAgeOrNull(currentState.dob)
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


    private fun calculateAgeOrNull(dobStr: String): Int? {
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
            age
        } catch (e: Exception) {
            null
        }
    }
    fun resetNavigationFlag() {
        _state.update { it.copy(isNavigateToDashboard = false) }
    }
}
package com.example.healthtracker.ui.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.domain.usecase.dashboard.CalculateBmiUseCase
import com.example.healthtracker.domain.usecase.settings.GetAppSettingsUseCase
import com.example.healthtracker.domain.usecase.settings.GetNotificationsEnabledUseCase
import com.example.healthtracker.domain.usecase.settings.UpdateAppLanguageUseCase
import com.example.healthtracker.domain.usecase.settings.UpdateAppModeUseCase
import com.example.healthtracker.domain.usecase.settings.UpdateAppThemeUseCase
import com.example.healthtracker.domain.usecase.settings.UpdateFontSizeUseCase
import com.example.healthtracker.domain.usecase.settings.UpdateNotificationsEnabledUseCase
import com.example.healthtracker.notification.ReminderScheduler
import com.example.healthtracker.ui.theme.AppFontSize
import com.example.healthtracker.ui.theme.AppThemeType
import com.example.healthtracker.ui.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsState(
    val currentTheme: AppThemeType = AppThemeType.DEFAULT,
    val currentMode: ThemeMode = ThemeMode.SYSTEM,
    val currentFontSize: AppFontSize = AppFontSize.MEDIUM,
    val appLanguage: String = "vi",
    val userName: String = "",
    val bmiValue: Float = 0f,
    val bmiCategory: BmiCategory = BmiCategory.UNDEFINED,
    val notificationsEnabled: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val updateAppThemeUseCase: UpdateAppThemeUseCase,
    private val updateAppModeUseCase: UpdateAppModeUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val updateAppLanguageUseCase: UpdateAppLanguageUseCase,
    private val updateFontSizeUseCase: UpdateFontSizeUseCase,
    private val getNotificationsEnabledUseCase: GetNotificationsEnabledUseCase,
    private val updateNotificationsEnabledUseCase: UpdateNotificationsEnabledUseCase,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        loadSettingsData()
        loadNotificationsSetting()
    }

    private fun loadNotificationsSetting() {
        viewModelScope.launch {
            getNotificationsEnabledUseCase().collect { enabled ->
                _state.value = _state.value.copy(notificationsEnabled = enabled)
            }
        }
    }

    private fun loadSettingsData() {
        viewModelScope.launch {
            getAppSettingsUseCase().collect { settings ->
                val profile = settings.userProfile

                val bmiResult = calculateBmiUseCase(profile.weight, profile.height)

                val theme = try {
                    AppThemeType.valueOf(settings.appTheme)
                } catch (e: Exception) {
                    AppThemeType.DEFAULT
                }

                val mode = try {
                    ThemeMode.valueOf(settings.appMode)
                } catch (e: Exception) {
                    ThemeMode.SYSTEM
                }

                val fontSize = try {
                    AppFontSize.valueOf(settings.fontSize)
                } catch (e: Exception) {
                    AppFontSize.MEDIUM
                }

                _state.value = _state.value.copy(
                    userName = profile.name,
                    bmiValue = bmiResult.bmi,
                    bmiCategory = bmiResult.category,
                    currentTheme = theme,
                    currentMode = mode,
                    currentFontSize = fontSize,
                    appLanguage = settings.appLanguage
                )
            }
        }
    }

    fun updateTheme(newTheme: AppThemeType) {
        viewModelScope.launch {
            updateAppThemeUseCase(newTheme.name)
        }
    }
    fun updateLanguage(langCode: String) {
        viewModelScope.launch {
            updateAppLanguageUseCase(langCode)
        }
    }
    fun updateFontSize(newSize: AppFontSize) {
        viewModelScope.launch {
            updateFontSizeUseCase(newSize.name)
        }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            updateNotificationsEnabledUseCase(enabled)
            reminderScheduler.refreshDailyReminders()
        }
    }

    fun sendTestNotification() {
        viewModelScope.launch {
            reminderScheduler.scheduleTestReminder()
        }
    }

    fun updateMode(newMode: ThemeMode) {
        viewModelScope.launch {
            updateAppModeUseCase(newMode.name)
        }
    }
}
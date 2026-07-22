package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class SettingsUiModel(
    val userProfile: UserProfile,
    val appTheme: String,
    val appMode: String,
    val appLanguage: String,
    val fontSize: String
)

class GetAppSettingsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<SettingsUiModel> {
        return combine(
            userRepository.userProfile,
            userRepository.appTheme,
            userRepository.appMode,
            userRepository.appLanguage,
            userRepository.fontSize
        ) { profile, theme, mode, language, size ->
            SettingsUiModel(
                userProfile = profile,
                appTheme = theme,
                appMode = mode,
                appLanguage = language,
                fontSize = size
            )
        }
    }
}
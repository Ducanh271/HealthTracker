package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.model.AppSettings
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAppSettingsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<AppSettings> {
        return combine(
            userRepository.userProfile,
            userRepository.appTheme,
            userRepository.appMode,
            userRepository.appLanguage,
            userRepository.fontSize
        ) { profile, theme, mode, language, size ->
            AppSettings(
                userProfile = profile,
                appTheme = theme,
                appMode = mode,
                appLanguage = language,
                fontSize = size
            )
        }
    }
}
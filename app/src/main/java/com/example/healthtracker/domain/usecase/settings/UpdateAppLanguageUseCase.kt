package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class UpdateAppLanguageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(languageCode: String) {
        // Lưu mã ngôn ngữ (VD: "vi" hoặc "en") xuống DataStore
        userRepository.saveAppLanguage(languageCode)
    }
}
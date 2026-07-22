package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class UpdateFontSizeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(fontSize: String) {
        userRepository.saveFontSize(fontSize)
    }
}
package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class UpdateAppModeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(mode: String) {
        userRepository.saveAppMode(mode)
    }
}
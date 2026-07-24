package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class UpdateNotificationsEnabledUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        userRepository.saveNotificationsEnabled(enabled)
    }
}

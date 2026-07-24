package com.example.healthtracker.domain.usecase.settings

import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsEnabledUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Boolean> = userRepository.notificationsEnabled
}

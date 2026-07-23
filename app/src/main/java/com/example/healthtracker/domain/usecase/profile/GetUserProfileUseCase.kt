package com.example.healthtracker.domain.usecase.profile

import com.example.healthtracker.domain.model.UserProfile
import com.example.healthtracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<UserProfile> {
        return userRepository.userProfile
    }
}
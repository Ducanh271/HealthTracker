package com.example.healthtracker.domain.usecase.profile

import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        age: Int,
        dob: String,
        gender: Gender,
        weight: Float,
        height: Float,
        activityLevel: Int,
        goal: Goal,
        tdee: Int
    ) {
        userRepository.saveUserProfile(
            name = name,
            age = age,
            dob = dob,
            gender = gender,
            weight = weight,
            height = height,
            activityLevel = activityLevel,
            goal = goal,
            tdee = tdee
        )
    }
}
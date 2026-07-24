package com.example.healthtracker.di

import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.dashboard.GetDashboardMetricsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun getDashboardMetricsUseCase(): GetDashboardMetricsUseCase
    fun userRepository(): UserRepository
}

package com.example.healthtracker.domain.usecase.activity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SelfImprovement
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchActivityItemsUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(query: String): Flow<List<ActivityCatalogItem>> {
        return activityRepository.searchActivityItems(query).map { entities ->
            entities.map { entity ->
                val (category, icon) = when {
                    entity.name.contains("Chạy") || entity.name.contains("Đi bộ") -> "Cardio" to Icons.Default.DirectionsRun
                    entity.name.contains("Đạp xe") -> "Cardio" to Icons.Default.PedalBike
                    entity.name.contains("Bơi") -> "Thể thao" to Icons.Default.Pool
                    entity.name.contains("tạ") || entity.name.contains("Gym") -> "Sức mạnh" to Icons.Default.FitnessCenter
                    entity.name.contains("Yoga") -> "Yoga" to Icons.Default.SelfImprovement
                    else -> "Khác" to Icons.Default.DirectionsRun
                }

                ActivityCatalogItem(
                    id = entity.id,
                    name = entity.name,
                    description = category,
                    metValue = entity.metValue,
                    category = category,
                    icon = icon
                )
            }
        }
    }
}
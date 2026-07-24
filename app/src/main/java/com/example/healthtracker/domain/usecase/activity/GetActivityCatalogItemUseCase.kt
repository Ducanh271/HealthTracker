package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.repository.ActivityRepository
import javax.inject.Inject

class GetActivityCatalogItemUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(name: String): ActivityCatalogItem? =
        activityRepository.getActivityItemByName(name)?.toCatalogItem()
}

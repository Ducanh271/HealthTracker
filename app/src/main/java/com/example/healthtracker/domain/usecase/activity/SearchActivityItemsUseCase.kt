package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchActivityItemsUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(query: String): Flow<List<ActivityCatalogItem>> {
        return activityRepository.searchActivityItems(query).map { entries ->
            entries.map { it.toCatalogItem() }
        }
    }
}

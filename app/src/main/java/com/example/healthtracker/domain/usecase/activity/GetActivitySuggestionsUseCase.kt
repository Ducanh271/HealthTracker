package com.example.healthtracker.domain.usecase.activity

import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetActivitySuggestionsUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(limit: Int = DEFAULT_LIMIT): Flow<List<ActivityCatalogItem>> {
        return combine(
            activityRepository.getRecentActivityItems(limit),
            activityRepository.getDefaultActivityItems(limit)
        ) { recent, defaults ->
            recent.ifEmpty { defaults }.map { it.toCatalogItem() }
        }
    }

    companion object {
        const val DEFAULT_LIMIT = 5
    }
}

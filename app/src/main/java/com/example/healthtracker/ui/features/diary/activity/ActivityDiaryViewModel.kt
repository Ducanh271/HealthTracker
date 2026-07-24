package com.example.healthtracker.ui.features.diary.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.model.ActivityItem
import com.example.healthtracker.domain.model.toCatalogItem
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.activity.AddActivityLogUseCase
import com.example.healthtracker.domain.usecase.activity.CalculateActivityCaloriesUseCase
import com.example.healthtracker.domain.usecase.activity.DeleteActivityLogUseCase
import com.example.healthtracker.domain.usecase.activity.GetActivityCatalogItemUseCase
import com.example.healthtracker.domain.usecase.activity.GetActivityDiaryUseCase
import com.example.healthtracker.domain.usecase.activity.GetActivitySuggestionsUseCase
import com.example.healthtracker.domain.usecase.activity.SearchActivityItemsUseCase
import com.example.healthtracker.domain.usecase.activity.UpdateActivityLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ActivityDiaryViewModel @Inject constructor(
    private val getActivityDiaryUseCase: GetActivityDiaryUseCase,
    private val addActivityLogUseCase: AddActivityLogUseCase,
    private val searchActivityItemsUseCase: SearchActivityItemsUseCase,
    private val calculateCaloriesUseCase: CalculateActivityCaloriesUseCase,
    private val deleteActivityLogUseCase: DeleteActivityLogUseCase,
    private val updateActivityLogUseCase: UpdateActivityLogUseCase,
    private val getActivitySuggestionsUseCase: GetActivitySuggestionsUseCase,
    private val getActivityCatalogItemUseCase: GetActivityCatalogItemUseCase,
    private val userRepository: UserRepository
    ) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())

    val state: StateFlow<ActivityDiaryState> = combine(
        _selectedDate,
        userRepository.userProfile
    ) { date, profile ->
        Pair(date, profile)
    }.flatMapLatest { (date, profile) ->
        val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

        getActivityDiaryUseCase(dateStr).map { summary ->
            ActivityDiaryState(
                selectedDate = date,
                totalBurnedCalories = summary.totalCalories,
                targetBurnCalories = (profile.tdee * BURN_TARGET_RATIO).toInt(),
                activities = summary.logs,
                userWeight = profile.weight
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ActivityDiaryState())

    val suggestions: StateFlow<List<ActivityCatalogItem>> = getActivitySuggestionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _logTarget = MutableStateFlow<ActivityLogTarget?>(null)
    val logTarget = _logTarget.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchResults: StateFlow<List<ActivityCatalogItem>> = _searchQuery
        .debounce(300.milliseconds)
        .flatMapLatest { query ->
            searchActivityItemsUseCase(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun calculateCalories(metValue: Float, durationMinutes: Int): Int {
        val weight = state.value.userWeight
        return calculateCaloriesUseCase(metValue, weight, durationMinutes)
    }
    fun startLogging(catalogItem: ActivityCatalogItem) {
        _logTarget.value = ActivityLogTarget(catalogItem = catalogItem)
    }

    fun startEditing(activity: ActivityItem) {
        viewModelScope.launch {
            val catalogItem = getActivityCatalogItemUseCase(activity.name) ?: activity.toCatalogItem()

            _logTarget.value = ActivityLogTarget(
                catalogItem = catalogItem,
                initialDuration = activity.duration,
                existingLogId = activity.id
            )
        }
    }

    fun dismissLogging() {
        _logTarget.value = null
    }

    fun confirmLogging(durationMinutes: Int, caloriesBurned: Int) {
        val target = _logTarget.value ?: return

        viewModelScope.launch {
            if (target.existingLogId != null) {
                updateActivityLogUseCase(target.existingLogId, durationMinutes, caloriesBurned)
            } else {
                addActivityLogUseCase(
                    date = _selectedDate.value.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    name = target.catalogItem.name,
                    duration = durationMinutes,
                    calories = caloriesBurned
                )
            }
            _logTarget.value = null
        }
    }

    fun deleteActivityLog(activityId: Int) {
        viewModelScope.launch {
            deleteActivityLogUseCase(activityId)
        }
    }

    companion object {
        private const val BURN_TARGET_RATIO = 0.3f
    }
}
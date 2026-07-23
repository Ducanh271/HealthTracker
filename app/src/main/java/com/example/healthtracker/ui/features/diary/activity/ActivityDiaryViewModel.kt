package com.example.healthtracker.ui.features.diary.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.domain.usecase.activity.AddActivityLogUseCase
import com.example.healthtracker.domain.usecase.activity.CalculateActivityCaloriesUseCase
import com.example.healthtracker.domain.usecase.activity.DeleteActivityLogUseCase
import com.example.healthtracker.domain.usecase.activity.GetActivityDiaryUseCase
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
    private val deleteActivityLogUseCase: DeleteActivityLogUseCase, // Bổ sung
    private val updateActivityLogUseCase: UpdateActivityLogUseCase,
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
    fun addActivityLog(name: String, durationMinutes: Int, caloriesBurned: Int) {
        viewModelScope.launch {
            val dateStr = _selectedDate.value.format(DateTimeFormatter.ISO_LOCAL_DATE)
            addActivityLogUseCase(
                date = dateStr,
                name = name,
                duration = durationMinutes,
                calories = caloriesBurned
            )
        }
    }
    fun deleteActivityLog(activityId: Int) {
        viewModelScope.launch {
            deleteActivityLogUseCase(activityId)
        }
    }

    fun updateActivityLog(id: Int, durationMinutes: Int, caloriesBurned: Int) {
        viewModelScope.launch {
            updateActivityLogUseCase(id, durationMinutes, caloriesBurned)
        }
    }

    companion object {
        private const val BURN_TARGET_RATIO = 0.3f
    }
}
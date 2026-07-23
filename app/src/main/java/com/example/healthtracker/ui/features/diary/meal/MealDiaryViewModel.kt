package com.example.healthtracker.ui.features.diary.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.domain.model.CartItem
import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.domain.usecase.meal.AddMealLogUseCase
import com.example.healthtracker.domain.usecase.meal.AddNewFoodItemUseCase
import com.example.healthtracker.domain.usecase.meal.DeleteMealLogUseCase
import com.example.healthtracker.domain.usecase.meal.GetMealDiaryUseCase
import com.example.healthtracker.domain.usecase.meal.SearchFoodItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds


@HiltViewModel
class MealDiaryViewModel @Inject constructor(
    private val getMealDiaryUseCase: GetMealDiaryUseCase,
    private val addMealLogUseCase: AddMealLogUseCase,
    private val deleteMealLogUseCase: DeleteMealLogUseCase,
    private val addNewFoodItemUseCase: AddNewFoodItemUseCase,
    private val searchFoodItemsUseCase: SearchFoodItemsUseCase
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MealDiaryState> = _selectedDate.flatMapLatest { date ->
        val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

        getMealDiaryUseCase(dateStr).map { diaryData ->
            MealDiaryState(
                selectedDate = date,
                targetCalories = diaryData.targetCalories,
                totalCalories = diaryData.totalCalories,
                breakfast = diaryData.breakfast,
                lunch = diaryData.lunch,
                dinner = diaryData.dinner,
                snacks = diaryData.snacks
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MealDiaryState()
    )

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
    }

    fun addFood(mealType: MealType, foodName: String, servingCount: Int, calories: Int) {
        viewModelScope.launch {
            val dateStr = _selectedDate.value.format(DateTimeFormatter.ISO_LOCAL_DATE)
            addMealLogUseCase(
                date = dateStr,
                type = mealType,
                name = foodName,
                serving = servingCount,
                calories = calories
            )
        }
    }

    fun deleteFood(meal: Meal) {
        viewModelScope.launch {
            deleteMealLogUseCase(meal)
        }
    }

    fun addNewFoodToDatabaseAndCart(
        name: String,
        servingSize: String,
        calories: Int,
        quantity: Int,
        onSuccess: (CartItem) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val realFoodItem = addNewFoodItemUseCase(
                    name = name,
                    servingSize = servingSize,
                    calories = calories
                )

                val newCartItem = CartItem(food = realFoodItem, quantity = quantity)
                onSuccess(newCartItem)

            } catch (e: Exception) {
            }
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResults: StateFlow<List<FoodItem>> = _searchQuery
        .debounce(300.milliseconds)
        .flatMapLatest { query ->
            searchFoodItemsUseCase(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
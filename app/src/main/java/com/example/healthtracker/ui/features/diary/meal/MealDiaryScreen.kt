package com.example.healthtracker.ui.features.diary.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness2
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.CartItem
import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.DailySummaryCard
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.DateSelectorBar
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.MealSection
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.SearchBarComponent
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog.AddNewFoodBottomSheet
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog.SearchFoodBottomSheet
import com.example.healthtracker.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDiaryScreen(
    viewModel: MealDiaryViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var currentMealType by remember { mutableStateOf(MealType.BREAKFAST) }

    var cartItems by remember { mutableStateOf(listOf<CartItem>()) }

    var showAddNewFoodSheet by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val resources = LocalResources.current
    LaunchedEffect(Unit) {
        viewModel.errorEvent.collect { messageRes ->
            Toast.makeText(context, resources.getString(messageRes), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.meal_diary_title), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = dimens.marginMobile)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(dimens.lg)
        ) {
            DateSelectorBar(
                selectedDate = state.selectedDate,
                onDateSelected = { viewModel.updateSelectedDate(it) }
            )

            DailySummaryCard(totalKcal = state.totalCalories, targetKcal = state.targetCalories)


            MealSection(
                title = stringResource(id = R.string.meal_breakfast),
                icon = Icons.Outlined.LightMode,
                foods = state.breakfast,
                onAddClick = {
                    currentMealType = MealType.BREAKFAST
                    showBottomSheet = true
                },
                onDeleteClick = { viewModel.deleteFood(it) }
            )

            MealSection(
                title = stringResource(id = R.string.meal_lunch),
                icon = Icons.Outlined.WbSunny,
                foods = state.lunch,
                onAddClick = {
                    currentMealType = MealType.LUNCH
                    showBottomSheet = true
                },
                onDeleteClick = { viewModel.deleteFood(it) }
            )

            MealSection(
                title = stringResource(id = R.string.meal_dinner),
                icon = Icons.Outlined.Brightness2,
                foods = state.dinner,
                onAddClick = {
                    currentMealType = MealType.DINNER
                    showBottomSheet = true
                },
                onDeleteClick = { viewModel.deleteFood(it) }
            )

            MealSection(
                title = stringResource(id = R.string.meal_snack),
                icon = Icons.Outlined.Fastfood,
                foods = state.snacks,
                onAddClick = {
                    currentMealType = MealType.SNACK
                    showBottomSheet = true
                },
                onDeleteClick = { viewModel.deleteFood(it) }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }


    if (showBottomSheet) {
        SearchFoodBottomSheet(
            mealType = currentMealType,
            onMealTypeChange = { currentMealType = it },
            cartItems = cartItems,
            onCartItemsChange = { cartItems = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            searchResults = searchResults,
            onDismiss = {
                showBottomSheet = false
                cartItems = emptyList()
                viewModel.updateSearchQuery("")
            },
            onConfirm = { confirmedCart ->
                confirmedCart.forEach { cartItem ->
                    viewModel.addFood(
                        mealType = currentMealType,
                        foodName = cartItem.food.name,
                        servingCount = cartItem.quantity,
                        calories = cartItem.food.calories * cartItem.quantity
                    )
                }
                showBottomSheet = false
                cartItems = emptyList()
                viewModel.updateSearchQuery("")
            },
            onAddNewFoodClick = {

                showBottomSheet = false
                showAddNewFoodSheet = true
            }
        )
    }

    if (showAddNewFoodSheet) {
        AddNewFoodBottomSheet(
            onDismiss = {
                showAddNewFoodSheet = false
                showBottomSheet = true
            },
            onSubmit = { name, servingSize, calories, quantity ->
                viewModel.addNewFoodToDatabaseAndCart(
                    name = name,
                    servingSize = servingSize,
                    calories = calories,
                    quantity = quantity
                ) { newCartItem ->
                    val existing = cartItems.find { it.food.id == newCartItem.food.id }
                    cartItems = if (existing != null) {
                        cartItems.map { if (it.food.id == newCartItem.food.id) it.copy(quantity = it.quantity + newCartItem.quantity) else it }
                    } else {
                        cartItems + newCartItem
                    }

                    showAddNewFoodSheet = false
                    showBottomSheet = true
                }
            }
        )
    }
}
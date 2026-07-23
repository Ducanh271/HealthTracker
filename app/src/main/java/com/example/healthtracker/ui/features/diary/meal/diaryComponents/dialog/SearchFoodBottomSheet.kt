package com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.CartItem
import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.domain.model.MealType
import com.example.healthtracker.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFoodBottomSheet(
    mealType: MealType,
    onMealTypeChange: (MealType) -> Unit,
    cartItems: List<CartItem>,
    onCartItemsChange: (List<CartItem>) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (List<CartItem>) -> Unit,
    onAddNewFoodClick: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchResults: List<FoodItem>
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val dimens = LocalDimens.current

    val totalCartCalories = cartItems.sumOf { it.food.calories * it.quantity }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outlineVariant) }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.9f)
        ) {
//            Column(modifier = Modifier.padding(horizontal = dimens.marginMobile, vertical = dimens.sm)) {
//                SheetHeader(mealType = mealType, onClose = onDismiss)
//                Spacer(modifier = Modifier.height(dimens.md))
//                SearchBar(query = searchQuery, onQueryChange = onSearchQueryChange)
//            }
            Column(modifier = Modifier.padding(horizontal = dimens.marginMobile, vertical = dimens.sm)) {
                // Header mới có nút Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.sheet_add_title, stringResource(id = mealType.labelRes())),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(dimens.xs))

                MealTypeSelector(
                    selectedMeal = mealType,
                    onMealSelected = onMealTypeChange
                )

                Spacer(modifier = Modifier.height(dimens.md))
                SearchBar(query = searchQuery, onQueryChange = onSearchQueryChange)
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimens.marginMobile),
                contentPadding = PaddingValues(vertical = dimens.md)
            ) {
                if (cartItems.isNotEmpty()) {
                    item {
                        SelectedFoodsSection(
                            cartItems = cartItems,
                            totalCalories = totalCartCalories,
                            onRemove = { itemToRemove ->
                                val newCart = cartItems.filter { it != itemToRemove }
                                onCartItemsChange(newCart)
                            }
                        )
                        Spacer(modifier = Modifier.height(dimens.xl))
                    }
                }

                item {
                    SectionTitle(
                        icon = Icons.Default.RestaurantMenu,
                        title = stringResource(id = R.string.sheet_food_list),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(dimens.md))
                }

                if (searchResults.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.sheet_empty_search),
                            modifier = Modifier.fillMaxWidth().padding(dimens.lg),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    items(searchResults) { food ->
                        FoodItemCard(
                            food = food,
                            onAdd = { quantity ->
                                val existing = cartItems.find { it.food.id == food.id }
                                val newCart = if (existing != null) {
                                    cartItems.map { if (it.food.id == food.id) it.copy(quantity = it.quantity + quantity) else it }
                                } else {
                                    cartItems + CartItem(food, quantity)
                                }
                                onCartItemsChange(newCart)
                            }
                        )
                        Spacer(modifier = Modifier.height(dimens.md))
                    }
                }
            }

            SheetFooter(
                hasItems = cartItems.isNotEmpty(),
                onAddNewFood = onAddNewFoodClick,
                onCancel = onDismiss,
                onConfirm = { onConfirm(cartItems) }
            )
        }
    }
}
@Composable
fun MealTypeSelector(
    selectedMeal: MealType,
    onMealSelected: (MealType) -> Unit
) {
    val meals = MealType.entries
    val dimens = LocalDimens.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimens.sm),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(meals) { meal ->
            val isSelected = meal == selectedMeal
            val bgColor =
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh
            val contentColor =
                if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimens.cornerFull))
                    .background(bgColor)
                    .clickable { onMealSelected(meal) }
                    .padding(horizontal = dimens.lg, vertical = dimens.sm)
            ) {
                Text(
                    text = stringResource(id = meal.labelRes()),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = contentColor
                )
            }
        }
    }
}

fun MealType.labelRes(): Int = when (this) {
    MealType.BREAKFAST -> R.string.meal_breakfast
    MealType.LUNCH -> R.string.meal_lunch
    MealType.DINNER -> R.string.meal_dinner
    MealType.SNACK -> R.string.meal_snack
}

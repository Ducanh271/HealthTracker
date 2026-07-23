package com.example.healthtracker.ui.features.diary.meal.diaryComponents


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.Meal
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun DailySummaryCard(totalKcal: Int, targetKcal: Int) {
    val dimens = LocalDimens.current
    val progress = if (targetKcal > 0) (totalKcal.toFloat() / targetKcal).coerceIn(0f, 1f) else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(dimens.lg)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.meal_diary_total_today),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$totalKcal",
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = " kcal",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.meal_diary_target, targetKcal),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(dimens.xs))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.width(80.dp).height(8.dp).clip(RoundedCornerShape(dimens.cornerFull)),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
fun SearchBarComponent(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val dimens = LocalDimens.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(id = R.string.meal_diary_search)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cornerMedium),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}
@Composable
fun MealSection(
    title: String,
    icon: ImageVector,
    foods: List<Meal>,
    onAddClick: () -> Unit,
    onDeleteClick: (Meal) -> Unit
) {
    val dimens = LocalDimens.current
    val sectionTotalKcal = foods.sumOf { it.totalCalories }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(dimens.borderWidth, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(dimens.cornerLarge))
            .padding(dimens.md)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = dimens.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(dimens.sm))
                Text(text = title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
            }
            Text(text = "$sectionTotalKcal kcal", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.outline)
        }

        // List of Foods
        if (foods.isEmpty()) {
            Text(
                text = stringResource(id = R.string.meal_diary_empty),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = dimens.md)
            )
        } else {
            var selectedFoodId by remember { mutableStateOf<Int?>(null) }

            foods.forEach { food ->
                val isSelected = selectedFoodId == food.id

                val backgroundColor = if (isSelected) MaterialTheme.colorScheme.surfaceContainerHigh
                else MaterialTheme.colorScheme.surfaceContainerLowest

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimens.sm)
                        .clip(RoundedCornerShape(dimens.cornerMedium))
                        .background(backgroundColor)
                        .clickable {
                            selectedFoodId = if (isSelected) null else food.id
                        }
                        .padding(dimens.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = food.foodName, style = MaterialTheme.typography.bodyLarge)
                        Text(text = "${stringResource(id = R.string.meal_serving_portion, food.servingCount)} - ${food.totalCalories} kcal", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    if (isSelected) {
                        IconButton(
                            onClick = {
                                onDeleteClick(food)
                                selectedFoodId = null
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimens.cornerMedium))
                .clickable { onAddClick() }
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(dimens.cornerMedium))
                .padding(dimens.sm),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(dimens.xs))
                Text(text = stringResource(id = R.string.meal_diary_add_food), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
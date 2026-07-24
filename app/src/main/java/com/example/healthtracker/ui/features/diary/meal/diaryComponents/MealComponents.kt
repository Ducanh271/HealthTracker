package com.example.healthtracker.ui.features.diary.meal.diaryComponents


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Restaurant
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
                FoodLogItem(
                    food = food,
                    isSelected = selectedFoodId == food.id,
                    onClick = {
                        selectedFoodId = if (selectedFoodId == food.id) null else food.id
                    },
                    onDelete = {
                        onDeleteClick(food)
                        selectedFoodId = null
                    }
                )
                Spacer(modifier = Modifier.height(dimens.sm))
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

@Composable
private fun FoodLogItem(
    food: Meal,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dimens = LocalDimens.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerMedium))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(
                dimens.borderWidth,
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f),
                RoundedCornerShape(dimens.cornerMedium)
            )
            .clickable { onClick() }
            .padding(horizontal = dimens.md, vertical = dimens.sm),
        horizontalArrangement = Arrangement.spacedBy(dimens.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(dimens.cornerMedium))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Restaurant,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimens.iconSmall)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = food.foodName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(id = R.string.meal_serving_portion, food.servingCount),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isSelected) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(dimens.iconLarge)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.sheet_action_delete),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(dimens.iconSmall)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimens.cornerFull))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = dimens.sm, vertical = dimens.xs)
            ) {
                Text(
                    text = stringResource(id = R.string.meal_kcal_value, food.totalCalories),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
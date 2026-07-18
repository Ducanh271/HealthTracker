package com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.CartItem
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun SelectedFoodsSection(
    cartItems: List<CartItem>,
    totalCalories: Int,
    onRemove: (CartItem) -> Unit
) {
    val dimens = LocalDimens.current
    Column {
        SectionTitle(
            icon = Icons.Default.CheckCircle,
            title = stringResource(id = R.string.sheet_selected_items),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(dimens.md))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimens.cornerLarge))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                .border(1.dp, MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f), RoundedCornerShape(dimens.cornerLarge))
                .padding(dimens.md)
        ) {
            cartItems.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = dimens.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.food.name, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            text = stringResource(id = R.string.sheet_item_info, item.quantity, item.food.servingSize, item.food.calories * item.quantity),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TextButton(onClick = { onRemove(item) }, contentPadding = PaddingValues(0.dp)) {
                        Text(stringResource(id = R.string.sheet_action_delete), color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = dimens.sm))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.sheet_meal_total), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Text("$totalCalories kcal", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
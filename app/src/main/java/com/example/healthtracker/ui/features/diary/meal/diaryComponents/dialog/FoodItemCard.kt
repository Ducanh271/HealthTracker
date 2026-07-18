package com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.FoodItem
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun FoodItemCard(food: FoodItem, onAdd: (Int) -> Unit) {
    val dimens = LocalDimens.current
    var count by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(dimens.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(dimens.cornerMedium))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.RestaurantMenu, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Spacer(modifier = Modifier.width(dimens.md))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = food.name, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
            Text(text = "${food.calories} kcal / ${food.servingSize}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(dimens.xs))
            Text(
                text = stringResource(id = R.string.sheet_item_count, count, food.calories * count),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .padding(horizontal = dimens.sm, vertical = 2.dp)
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceBright, CircleShape)
            ) {
                IconButton(onClick = { if (count > 1) count-- }, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = "-", modifier = Modifier.size(18.dp))
                }
                Text(text = count.toString(), style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(horizontal = dimens.xs))
                IconButton(onClick = { count++ }, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "+", modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(dimens.sm))
            Button(
                onClick = {
                    onAdd(count)
                    count = 1
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(horizontal = dimens.lg, vertical = dimens.xs),
                modifier = Modifier.height(32.dp)
            ) {
                Text(stringResource(id = R.string.sheet_action_add), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
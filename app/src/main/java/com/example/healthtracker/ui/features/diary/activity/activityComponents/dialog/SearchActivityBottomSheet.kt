package com.example.healthtracker.ui.features.diary.activity.activityComponents.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.ui.theme.LocalDimens



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchActivityBottomSheet(
    onDismiss: () -> Unit,
    onAddManualClick: () -> Unit,
    onActivitySelect: (ActivityCatalogItem) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchResults: List<ActivityCatalogItem>
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val dimens = LocalDimens.current

    var selectedCategory by remember { mutableStateOf("Tất cả") }

    val categories = listOf(
        stringResource(id = R.string.activity_category_all),
        stringResource(id = R.string.activity_category_cardio),
        stringResource(id = R.string.activity_category_strength),
        stringResource(id = R.string.activity_category_yoga),
        stringResource(id = R.string.activity_category_sports)
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)) }
    ) {
        Column(modifier = Modifier.fillMaxHeight(0.9f)) {

            // 1. Header
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.marginMobile, vertical = dimens.sm),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.activity_search_title),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // 2. Search & Categories
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = dimens.marginMobile, vertical = dimens.sm)
            ) {
                ActivitySearchBar(query = searchQuery, onQueryChange = { onSearchQueryChange(it)})
                Spacer(modifier = Modifier.height(dimens.md))
                ActivityCategoryChips(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelect = { selectedCategory = it }
                )
            }

            // 3. Activity List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimens.marginMobile),
                contentPadding = PaddingValues(vertical = dimens.sm)
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.activity_popular_title).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = dimens.xs, vertical = dimens.md)
                    )
                }

                items(searchResults) { activity ->
                    ActivityCatalogItemCard(item = activity, onClick = { onActivitySelect(activity) })
                    Spacer(modifier = Modifier.height(dimens.xs))
                }
            }

            // 4. Footer
            ActivitySearchFooter(
                onCancel = onDismiss,
                onAddManual = onAddManualClick
            )
        }
    }
}

// ================= COMPONENT CON =================

@Composable
private fun ActivitySearchBar(query: String, onQueryChange: (String) -> Unit) {
    val dimens = LocalDimens.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(id = R.string.activity_search_hint)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cornerFull),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun ActivityCategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    val dimens = LocalDimens.current
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimens.sm),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimens.cornerFull))
                    .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(dimens.cornerFull)
                    )
                    .clickable { onCategorySelect(category) }
                    .padding(horizontal = dimens.md, vertical = 6.dp)
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ActivityCatalogItemCard(item: ActivityCatalogItem, onClick: () -> Unit) {
    val dimens = LocalDimens.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .clickable { onClick() }
            .padding(dimens.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.width(dimens.md))

        // Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // MET Value Pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.cornerFull))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(horizontal = dimens.md, vertical = dimens.xs)
        ) {
            Text(
                text = stringResource(id = R.string.activity_met_format, item.metValue),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ActivitySearchFooter(
    onCancel: () -> Unit,
    onAddManual: () -> Unit
) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            .padding(dimens.marginMobile),
        horizontalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        TextButton(
            onClick = onCancel,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(dimens.cornerLarge)
        ) {
            Text(
                text = stringResource(id = R.string.activity_action_cancel),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(
            onClick = onAddManual,
            modifier = Modifier
                .weight(2f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(dimens.cornerLarge)
        ) {
            Text(
                text = stringResource(id = R.string.activity_action_add_manual),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
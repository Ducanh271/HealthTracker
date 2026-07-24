package com.example.healthtracker.ui.features.diary.activity.activityComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.model.ActivityItem
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun ActivitySummaryCard(totalBurned: Int, progressPercent: Int) {
    val dimens = LocalDimens.current
    val progress = (progressPercent / 100f).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(dimens.lg)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.activity_total_burned),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                )
                Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(top = dimens.xs)) {
                    Text(
                        text = totalBurned.toString(),
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(dimens.xs))
                    Text(
                        text = stringResource(id = R.string.activity_unit_kcal),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = dimens.md)) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(dimens.xs))
                    Text(
                        text = stringResource(id = R.string.activity_goal_progress, progressPercent),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(96.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                    strokeWidth = 8.dp,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun QuickSuggestionsSection(
    suggestions: List<ActivityCatalogItem>,
    onClick: (ActivityCatalogItem) -> Unit
) {
    val dimens = LocalDimens.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.activity_quick_suggestions),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(horizontal = dimens.xs)
                .padding(bottom = dimens.md)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimens.md),
            contentPadding = PaddingValues(end = dimens.lg)
        ) {
            items(suggestions) { suggestion ->
                val color = MaterialTheme.colorScheme.primary

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimens.cornerLarge))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(dimens.cornerLarge))
                        .clickable { onClick(suggestion) }
                        .padding(horizontal = dimens.md, vertical = dimens.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = suggestion.icon, contentDescription = null, tint = color)
                    }
                    Spacer(modifier = Modifier.width(dimens.md))
                    Column {
                        Text(text = suggestion.name, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            text = stringResource(id = R.string.activity_met_value, suggestion.metValue),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityListSection(
    activities: List<ActivityItem>,
    onItemClick: (ActivityItem) -> Unit,
    selectedId: Int?,
    onDelete: () -> Unit,
    onEdit: (ActivityItem) -> Unit
) {
    val dimens = LocalDimens.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = dimens.xs)
                .padding(bottom = dimens.md)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.activity_today),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextButton(onClick = { /* TODO */ }, contentPadding = PaddingValues(0.dp)) {
                Text(
                    text = stringResource(id = R.string.activity_see_all).uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (activities.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = dimens.xl),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), modifier = Modifier.size(32.dp))
                }
                Spacer(modifier = Modifier.height(dimens.sm))
                Text(
                    text = stringResource(id = R.string.activity_empty_state),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            activities.forEach { activity ->
                ActivityItemCard(item = activity,
                    isSelected = (activity.id == selectedId),
                    onSelect = { onItemClick(activity) },
                    onDelete = { onDelete() },
                    onEdit = { onEdit(activity) }
                )
                Spacer(modifier = Modifier.height(dimens.sm))
            }
        }
    }
}
@Composable
fun ActivityItemCard(
    item: ActivityItem,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val dimens = LocalDimens.current
    val (icon, color) = getActivityConfig(item.type)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(if (isSelected) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surfaceContainerLow)
            .clickable { onSelect() }
            .padding(dimens.md)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.width(dimens.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.labelLarge)
                Text(text = "${item.duration} phút", style = MaterialTheme.typography.labelSmall)
            }
            Text(text = "${item.caloriesBurned} kcal", style = MaterialTheme.typography.labelLarge, color = color)
        }

        if (isSelected) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = dimens.sm),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDelete) {
                    Text("Xóa", color = MaterialTheme.colorScheme.error)
                }
                TextButton(onClick = onEdit) {
                    Text("Chỉnh sửa", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun getActivityConfig(type: ActivityType): Pair<ImageVector, Color> {
    return when (type) {
        ActivityType.STRENGTH -> Pair(Icons.Default.FitnessCenter, MaterialTheme.colorScheme.primary)
        ActivityType.CARDIO -> Pair(Icons.Default.DirectionsRun, MaterialTheme.colorScheme.tertiary)
        ActivityType.CYCLING -> Pair(Icons.Default.DirectionsBike, MaterialTheme.colorScheme.secondary)
        ActivityType.SWIMMING -> Pair(Icons.Default.Pool, MaterialTheme.colorScheme.secondary)
        ActivityType.WALKING -> Pair(Icons.Default.DirectionsWalk, MaterialTheme.colorScheme.primary)
    }
}
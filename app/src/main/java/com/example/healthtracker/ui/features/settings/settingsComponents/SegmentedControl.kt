package com.example.healthtracker.ui.features.settings.settingsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun <T> SegmentedControl(
    title: String,
    icon: ImageVector,
    items: List<Pair<String, T>>,
    selectedItem: T,
    onItemSelection: (T) -> Unit
) {
    val dimens = LocalDimens.current

    Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.sm),
            modifier = Modifier.padding(horizontal = dimens.xs)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Surface(
            shape = RoundedCornerShape(dimens.cornerLarge),
            color = MaterialTheme.colorScheme.surfaceContainer
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.xs)
            ) {
                items.forEach { (label, item) ->
                    val isSelected = selectedItem == item
                    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent
                    val contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(dimens.cornerLarge))
                            .background(backgroundColor)
                            .clickable { onItemSelection(item) }
                            .padding(vertical = dimens.sm),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge,
                            color = contentColor
                        )
                    }
                }
            }
        }
    }
}

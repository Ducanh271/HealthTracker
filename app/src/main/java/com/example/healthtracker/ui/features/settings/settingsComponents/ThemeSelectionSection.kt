package com.example.healthtracker.ui.features.settings.settingsComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.AppThemeType
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun ThemeSelectionSection(
    currentTheme: AppThemeType,
    onThemeSelected: (AppThemeType) -> Unit
) {
    val dimens = LocalDimens.current

    Column(verticalArrangement = Arrangement.spacedBy(dimens.md)) {
        Text(
            text = stringResource(id = R.string.settings_theme_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = dimens.xs)
        )

        val themes = AppThemeType.values().toList()

        Column(verticalArrangement = Arrangement.spacedBy(dimens.md)) {
            themes.chunked(2).forEach { rowThemes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.md)
                ) {
                    rowThemes.forEach { theme ->
                        ThemeCard(
                            title = stringResource(id = theme.stringResId),
                            color = theme.color,
                            isSelected = currentTheme == theme,
                            modifier = Modifier.weight(1f),
                            onClick = { onThemeSelected(theme) }
                        )
                    }

                    if (rowThemes.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeCard(
    title: String,
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val dimens = LocalDimens.current
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant

    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(dimens.cornerMedium),
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(dimens.sm),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.sm)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

package com.example.healthtracker.ui.features.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.ui.components.labelRes
import com.example.healthtracker.ui.theme.AppFontSize
import com.example.healthtracker.ui.theme.AppThemeType
import com.example.healthtracker.ui.theme.LocalDimens
import com.example.healthtracker.ui.theme.ThemeMode

// MAIN SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onEditProfileClick: () -> Unit = {}
) {
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings_title),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.action_back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
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
            verticalArrangement = Arrangement.spacedBy(dimens.xl)
        ) {
            Spacer(modifier = Modifier.height(dimens.sm))

            ProfileSection(
                userName = state.userName,
                bmiValue = state.bmiValue,
                bmiCategory = state.bmiCategory,
                onEditClick = onEditProfileClick
            )

            SettingsMenuSection(
                currentLanguage = state.appLanguage,
                onLanguageChange = { newLangCode ->
                    viewModel.updateLanguage(newLangCode)
                }
            )

            ThemeSelectionSection(
                currentTheme = state.currentTheme,
                onThemeSelected = { viewModel.updateTheme(it) }
            )

            SegmentedControl(
                title = stringResource(id = R.string.settings_brightness),
                icon = Icons.Default.BrightnessMedium,
                items = listOf(
                    stringResource(id = R.string.mode_light) to ThemeMode.LIGHT,
                    stringResource(id = R.string.mode_dark) to ThemeMode.DARK,
                    stringResource(id = R.string.mode_system) to ThemeMode.SYSTEM
                ),
                selectedItem = state.currentMode,
                onItemSelection = { viewModel.updateMode(it) }
            )

            SegmentedControl(
                title = stringResource(id = R.string.settings_font_size),
                icon = Icons.Default.TextFields,
                items = listOf(
                    stringResource(id = R.string.font_small) to AppFontSize.SMALL,
                    stringResource(id = R.string.font_medium) to AppFontSize.MEDIUM,
                    stringResource(id = R.string.font_large) to AppFontSize.LARGE
                ),
                selectedItem = state.currentFontSize,
                onItemSelection = { viewModel.updateFontSize(it) }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// SUB-COMPONENTS


@Composable
private fun ProfileSection(
    userName: String,
    bmiValue: Float,
    bmiCategory: BmiCategory,
    onEditClick: () -> Unit
) {
    val dimens = LocalDimens.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick() },
        shape = RoundedCornerShape(dimens.cornerLarge),
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(dimens.lg),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.lg)
        ) {
            Box(modifier = Modifier.size(80.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userName.ifEmpty { stringResource(id = R.string.settings_default_username) },
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.padding(top = dimens.xs),
                    horizontalArrangement = Arrangement.spacedBy(dimens.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(dimens.cornerLarge)
                    ) {
                        Text(
                            text = "BMI: $bmiValue",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Text(
                        text = stringResource(id = bmiCategory.labelRes()),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsMenuSection(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val dimens = LocalDimens.current

    val isEnglish = currentLanguage.lowercase() == "en"

    Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
        SettingsSwitchItem(
            icon = Icons.Default.Language,
            title = if (isEnglish) "English" else stringResource(id = R.string.lang_vietnamese),
            checked = isEnglish,
            onCheckedChange = { isChecked ->
                onLanguageChange(if (isChecked) "en" else "vi")
            }
        )

        SettingsSwitchItem(
            icon = Icons.Default.Notifications,
            title = stringResource(id = R.string.settings_notifications),
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
private fun ThemeSelectionSection(
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

@Composable
fun <T> SegmentedControl(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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

@Composable
private fun SettingsCardItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val dimens = LocalDimens.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(dimens.cornerMedium),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier.padding(dimens.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.md)
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
                Column {
                    Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = subtitle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val dimens = LocalDimens.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cornerMedium),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier.padding(dimens.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.md)
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
                Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}
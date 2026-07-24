package com.example.healthtracker.ui.features.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.ui.features.settings.settingsComponents.ProfileSection
import com.example.healthtracker.ui.features.settings.settingsComponents.SegmentedControl
import com.example.healthtracker.ui.features.settings.settingsComponents.SettingsMenuSection
import com.example.healthtracker.ui.features.settings.settingsComponents.ThemeSelectionSection
import com.example.healthtracker.ui.theme.AppFontSize
import com.example.healthtracker.ui.theme.LocalDimens
import com.example.healthtracker.ui.theme.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onEditProfileClick: () -> Unit = {}
) {
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val testNotificationSentMessage = stringResource(id = R.string.settings_test_notification_sent)

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
                notificationsEnabled = state.notificationsEnabled,
                onLanguageChange = { newLangCode ->
                    viewModel.updateLanguage(newLangCode)
                },
                onNotificationsEnabledChange = { viewModel.updateNotificationsEnabled(it) },
                onTestNotificationClick = {
                    viewModel.sendTestNotification()
                    Toast.makeText(
                        context,
                        testNotificationSentMessage,
                        Toast.LENGTH_SHORT
                    ).show()
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

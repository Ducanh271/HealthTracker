package com.example.healthtracker.ui.features.settings.settingsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun SettingsMenuSection(
    currentLanguage: String,
    notificationsEnabled: Boolean,
    onLanguageChange: (String) -> Unit,
    onNotificationsEnabledChange: (Boolean) -> Unit,
    onTestNotificationClick: () -> Unit
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
            subtitle = stringResource(id = R.string.settings_notifications_desc),
            checked = notificationsEnabled,
            onCheckedChange = onNotificationsEnabledChange
        )

        SettingsCardItem(
            icon = Icons.Default.NotificationsActive,
            title = stringResource(id = R.string.settings_test_notification),
            subtitle = stringResource(id = R.string.settings_test_notification_desc),
            enabled = notificationsEnabled,
            onClick = onTestNotificationClick
        )
    }
}

@Composable
private fun SettingsCardItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val dimens = LocalDimens.current
    val contentAlpha = if (enabled) 1f else 0.4f

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(contentAlpha)
            .clickable(enabled = enabled) { onClick() },
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
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    subtitle: String? = null
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
                Column {
                    Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    if (subtitle != null) {
                        Text(text = subtitle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

package com.example.healthtracker.ui.features.profile.editProfileComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.ui.components.labelRes
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun BentoMetricsSection(bmiValue: Float, bmiCategory: BmiCategory, tdee: Int) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        // BMI Card
        Surface(
            modifier = Modifier.weight(1f).aspectRatio(dimens.bentoCardAspect),
            shape = RoundedCornerShape(dimens.cornerLarge),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            border = BorderStroke(dimens.borderWidth, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(dimens.md),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(id = R.string.edit_profile_current_bmi), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(dimens.iconMedium))
                }
                Column(verticalArrangement = Arrangement.spacedBy(dimens.spacingXS)) {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(dimens.sm)) {
                        Text(text = bmiValue.toString(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Surface(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = RoundedCornerShape(dimens.cornerFull)) {
                            Text(text = stringResource(id = bmiCategory.labelRes()), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = dimens.sm, vertical = dimens.spacingXS))
                        }
                    }
                    Text(text = stringResource(id = R.string.edit_profile_bmi_desc), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                }
            }
        }

        Surface(
            modifier = Modifier.weight(1f).aspectRatio(dimens.bentoCardAspect),
            shape = RoundedCornerShape(dimens.cornerLarge),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
            border = BorderStroke(dimens.borderWidth, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(dimens.md),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(id = R.string.edit_profile_est_tdee), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(dimens.iconMedium))
                }
                Column(verticalArrangement = Arrangement.spacedBy(dimens.spacingXS)) {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(dimens.xs)) {
                        Text(text = tdee.toString(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text(text = stringResource(id = R.string.edit_profile_kcal_day), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.padding(bottom = dimens.xs))
                    }
                    Text(text = stringResource(id = R.string.edit_profile_tdee_desc), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f), maxLines = 1)
                }
            }
        }
    }
}
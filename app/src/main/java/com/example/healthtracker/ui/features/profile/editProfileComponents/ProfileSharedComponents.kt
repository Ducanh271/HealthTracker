package com.example.healthtracker.ui.features.profile.editProfileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun ProfileMetricInput(
    modifier: Modifier = Modifier, label: String, value: String,
    onValueChange: (String) -> Unit, unit: String, placeholder: String,
    errorMessage: String? = null, maxLength: Int = 5
) {
    val dimens = LocalDimens.current
    val isError = errorMessage != null

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimens.cornerMedium))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .border(
                    width = dimens.borderWidth,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(dimens.cornerMedium)
                )
                .padding(dimens.md),
            verticalArrangement = Arrangement.spacedBy(dimens.sm),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= maxLength) {
                            val isNumberOrDot = newValue.all { it.isDigit() || it == '.' }
                            val hasMaxOneDot = newValue.count { it == '.' } <= 1
                            if (isNumberOrDot && hasMaxOneDot) {
                                onValueChange(newValue)
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerTextField()
                    }
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = dimens.spacingXS, top = dimens.sm)
                )
            }
        }
        if (isError) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = dimens.xs, start = dimens.sm)
            )
        }
    }
}

@Composable
fun UnderlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    suffix: String? = null,
    isError: Boolean = false,
    errorText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val dimens = LocalDimens.current
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = dimens.sm)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(dimens.cornerSmall))
                        .border(
                            width = dimens.borderWidth,
                            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(dimens.cornerSmall)
                        )
                        .padding(horizontal = dimens.md, vertical = dimens.cornerMedium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(text = "", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        innerTextField()
                    }
                    icon?.let {
                        Spacer(modifier = Modifier.width(dimens.sm))
                        Icon(imageVector = it, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(dimens.iconEditBadge))
                    }
                    suffix?.let {
                        Spacer(modifier = Modifier.width(dimens.sm))
                        Text(text = it, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        )
        if (isError) {
            Text(text = errorText, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = dimens.xs))
        }
    }
}

@Composable
fun <T> SegmentedButton(items: List<Pair<String, T>>, selectedItem: T, onItemSelected: (T) -> Unit) {
    val dimens = LocalDimens.current
    Surface(
        shape = RoundedCornerShape(dimens.cornerSmall),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        modifier = Modifier.fillMaxWidth().height(dimens.inputMinWidth)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(dimens.spacingXS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { (label, item) ->
                val isSelected = selectedItem == item
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(dimens.cornerSmall - dimens.spacingXS))
                        .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                        .clickable { onItemSelected(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun BottomActionArea(isLoading: Boolean, onSaveClick: () -> Unit) {
    val dimens = LocalDimens.current
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = dimens.lg
    ) {
        Box(modifier = Modifier.padding(dimens.md)) {
            Button(
                onClick = onSaveClick,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth().height(dimens.buttonHeight),
                shape = RoundedCornerShape(dimens.cornerFull),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(dimens.iconMedium),
                        strokeWidth = dimens.strokeWidth
                    )
                    Spacer(modifier = Modifier.width(dimens.md))
                    Text(text = stringResource(id = R.string.edit_profile_saving), style = MaterialTheme.typography.titleMedium)
                } else {
                    Text(text = stringResource(id = R.string.edit_profile_save_changes), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(dimens.sm))
                    Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(dimens.iconEditBadge))
                }
            }
        }
    }
}
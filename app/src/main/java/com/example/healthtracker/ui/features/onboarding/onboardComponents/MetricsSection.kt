package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens
import java.util.Locale

@Composable
fun MetricsSection(
    weight: String, onWeightChange: (String) -> Unit, weightError: Int?,
    height: String, onHeightChange: (String) -> Unit, heightError: Int?
) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        MetricInput(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.label_weight),
            value = weight,
            onValueChange = onWeightChange,
            unit = stringResource(id = R.string.unit_kg),
            placeholder = stringResource(id = R.string.placeholder_weight),
            errorMessage = weightError?.let { stringResource(id = it) },
            step = 0.5f,
            minValue = 5f,
            maxValue = 300f,
            maxLength = 5 // Cho phép nhập 5 ký tự (vd: 120.5)
        )
        MetricInput(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.label_height),
            value = height,
            onValueChange = onHeightChange,
            unit = stringResource(id = R.string.unit_cm),
            placeholder = stringResource(id = R.string.placeholder_height),
            errorMessage = heightError?.let { stringResource(id = it) },
            step = 1f,
            minValue = 50f,
            maxValue = 300f,
            maxLength = 5 // Cho phép nhập 5 ký tự (vd: 170.5)
        )
    }
}

@Composable
private fun MetricInput(
    modifier: Modifier = Modifier, label: String, value: String,
    onValueChange: (String) -> Unit, unit: String, placeholder: String,
    errorMessage: String? = null, step: Float, minValue: Float, maxValue: Float,
    maxLength: Int
) {
    val dimens = LocalDimens.current
    val isError = errorMessage != null

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.cornerLarge))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .border(
                    width = dimens.borderWidth,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(dimens.cornerLarge)
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = {
                        val current = value.toFloatOrNull() ?: minValue
                        val next = (current - step).coerceAtLeast(minValue)
                        onValueChange(String.format(Locale.US, if(step % 1f == 0f) "%.0f" else "%.1f", next))
                    },
                    modifier = Modifier.size(dimens.xl)
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Giảm",
                        modifier = Modifier.padding(dimens.xs),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f).padding(horizontal = dimens.xs), // Dàn đều phần thừa
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

                // Nút Tăng
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = {
                        val current = value.toFloatOrNull() ?: minValue
                        val next = (current + step).coerceAtMost(maxValue)
                        onValueChange(String.format(Locale.US, if(step % 1f == 0f) "%.0f" else "%.1f", next))
                    },
                    modifier = Modifier.size(dimens.xl)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Tăng",
                        modifier = Modifier.padding(dimens.xs),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
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
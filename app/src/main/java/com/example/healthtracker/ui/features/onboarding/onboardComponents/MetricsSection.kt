package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

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
            errorMessage = weightError?.let { stringResource(id = it) }
        )
        MetricInput(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.label_height),
            value = height,
            onValueChange = onHeightChange,
            unit = stringResource(id = R.string.unit_cm),
            placeholder = stringResource(id = R.string.placeholder_height),
            errorMessage = heightError?.let { stringResource(id = it) }
        )
    }
}

@Composable
private fun MetricInput(
    modifier: Modifier = Modifier, label: String, value: String,
    onValueChange: (String) -> Unit, unit: String, placeholder: String,
    errorMessage: String? = null
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
            verticalArrangement = Arrangement.spacedBy(dimens.xs)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
            Row(verticalAlignment = Alignment.Bottom) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                        innerTextField()
                    }
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = dimens.xs)
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
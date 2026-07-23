package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.ui.theme.LocalDimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInfoCard(
    name: String, onNameChange: (String) -> Unit, nameError: Int?,
    dob: String, onDobChange: (String) -> Unit, dobError: Int?,
    gender: Gender, onGenderChange: (Gender) -> Unit
) {
    val dimens = LocalDimens.current
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        onDobChange(formatter.format(Date(millis)))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(id = R.string.action_confirm ?: android.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(id = R.string.action_cancel ?: android.R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(dimens.borderWidth, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(dimens.cornerLarge))
            .padding(dimens.md),
        verticalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(id = R.string.label_fullname)) },
            placeholder = { Text(stringResource(id = R.string.placeholder_fullname)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimens.cornerMedium),
            isError = nameError != null,
            supportingText = {
                if (nameError != null) {
                    Text(text = stringResource(id = nameError), color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimens.md),
            verticalAlignment = Alignment.Top
        ) {
            Box(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = dob,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.label_dob)) },
                    placeholder = { Text(stringResource(id = R.string.placeholder_dob)) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Chọn ngày")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimens.cornerMedium),
                    isError = dobError != null,
                    supportingText = {
                        if (dobError != null) {
                            Text(text = stringResource(id = dobError), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { showDatePicker = true }
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = dimens.sm),
                verticalArrangement = Arrangement.spacedBy(dimens.xs)
            ) {
//                Text(
//                    text = stringResource(id = R.string.label_gender),
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.padding(start = dimens.xs)
//                )
                GenderSelector(currentGender = gender, onGenderChange = onGenderChange)

                if (dobError != null) {
                    Spacer(modifier = Modifier.height(dimens.md))
                }
            }
        }
    }
}
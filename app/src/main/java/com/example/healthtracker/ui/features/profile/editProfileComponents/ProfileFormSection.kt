package com.example.healthtracker.ui.features.profile.editProfileComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.Goal
import com.example.healthtracker.ui.features.profile.EditProfileEvent
import com.example.healthtracker.ui.features.profile.EditProfileState
import com.example.healthtracker.ui.theme.LocalDimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormSection(state: EditProfileState, onEvent: (EditProfileEvent) -> Unit) {
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
                        // ĐÃ SỬA: Dùng OnDobChange thay vì OnAgeChange
                        onEvent(EditProfileEvent.OnDobChange(formatter.format(Date(millis))))
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

    Surface(
        shape = RoundedCornerShape(dimens.cornerLarge),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(dimens.borderWidth, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(dimens.lg),
            verticalArrangement = Arrangement.spacedBy(dimens.lg)
        ) {

            UnderlinedTextField(
                label = stringResource(id = R.string.label_fullname),
                value = state.name,
                onValueChange = { onEvent(EditProfileEvent.OnNameChange(it)) },
                icon = Icons.Default.Person
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.md),
                verticalAlignment = Alignment.Top
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    UnderlinedTextField(
                        label = stringResource(id = R.string.label_dob),
                        value = state.dob,
                        onValueChange = { },
                        icon = Icons.Default.DateRange
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.label_gender),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = dimens.sm)
                    )
                    SegmentedButton(
                        items = listOf(
                            stringResource(id = R.string.gender_male) to Gender.MALE,
                            stringResource(id = R.string.gender_female) to Gender.FEMALE
                        ),
                        selectedItem = state.gender,
                        onItemSelected = { onEvent(EditProfileEvent.OnGenderChange(it)) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileMetricInput(
                    modifier = Modifier.weight(1f),
                    label = stringResource(id = R.string.label_weight),
                    value = state.weight,
                    onValueChange = { onEvent(EditProfileEvent.OnWeightChange(it)) },
                    unit = stringResource(id = R.string.unit_kg),
                    placeholder = "00.0",
                    errorMessage = if (state.weightError) stringResource(id = R.string.error_weight_invalid) else null
                )
                ProfileMetricInput(
                    modifier = Modifier.weight(1f),
                    label = stringResource(id = R.string.label_height),
                    value = state.height,
                    onValueChange = { onEvent(EditProfileEvent.OnHeightChange(it)) },
                    unit = stringResource(id = R.string.unit_cm),
                    placeholder = "000.0",
                    errorMessage = if (state.heightError) stringResource(id = R.string.error_height_invalid) else null
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(dimens.xs)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(id = R.string.label_activity_level), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    Text(text = "Cấp độ ${state.activityLevel.toInt()}/5", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = state.activityLevel,
                    onValueChange = { onEvent(EditProfileEvent.OnActivityLevelChange(it)) },
                    valueRange = 1f..5f,
                    steps = 3,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(dimens.xs)) {
                Text(
                    text = stringResource(id = R.string.label_goal),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = dimens.xs)
                )
                SegmentedButton(
                    items = listOf(
                        stringResource(id = R.string.goal_value_lose) to Goal.LOSE_WEIGHT,
                        stringResource(id = R.string.goal_value_maintain) to Goal.MAINTAIN_WEIGHT,
                        stringResource(id = R.string.goal_value_gain) to Goal.GAIN_WEIGHT
                    ),
                    selectedItem = state.goal,
                    onItemSelected = { onEvent(EditProfileEvent.OnGoalChange(it)) }
                )
            }
        }
    }
}
package com.example.healthtracker.ui.features.profile

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.BmiCategory
import com.example.healthtracker.ui.theme.LocalDimens
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    LaunchedEffect(state.showSuccessToast) {
        if (state.showSuccessToast) {
            delay(1500)
            viewModel.onEvent(EditProfileEvent.OnToastDismiss)
            onNavigateBack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.edit_profile_title),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
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
            },
            bottomBar = {
                BottomActionArea(
                    isLoading = state.isLoading,
                    onSaveClick = { viewModel.onEvent(EditProfileEvent.OnSaveClick) }
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
                verticalArrangement = Arrangement.spacedBy(dimens.lg)
            ) {
                Spacer(modifier = Modifier.height(dimens.sm))

                AvatarSection()

                BentoMetricsSection(bmiValue = state.bmiValue, bmiCategory = state.bmiCategory, tdee = state.tdeeValue)

                ProfileFormSection(state = state, onEvent = viewModel::onEvent)

                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        // Toast Notification
        AnimatedVisibility(
            visible = state.showSuccessToast,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = dimens.xl * 2)
                .zIndex(60f)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(dimens.cornerFull),
                shadowElevation = dimens.sm
            ) {
                Text(
                    text = stringResource(id = R.string.edit_profile_save_success),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.padding(horizontal = dimens.lg, vertical = dimens.sm),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Composable
private fun AvatarSection() {
    val dimens = LocalDimens.current
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.size(110.dp).clickable { }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(54.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(dimens.sm))
        Text(
            text = stringResource(id = R.string.edit_profile_change_avatar),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BentoMetricsSection(bmiValue: Float, bmiCategory: BmiCategory, tdee: Int) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        // BMI Card
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(dimens.cornerLarge),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(dimens.md).height(130.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(id = R.string.edit_profile_current_bmi), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = bmiValue.toString(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Surface(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = RoundedCornerShape(dimens.cornerFull)) {
                            Text(text = bmiCategory.name, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    Text(text = stringResource(id = R.string.edit_profile_bmi_desc), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                }
            }
        }

        // TDEE Card
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(dimens.cornerLarge),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(dimens.md).height(130.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(id = R.string.edit_profile_est_tdee), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(20.dp))
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = tdee.toString(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text(text = stringResource(id = R.string.edit_profile_kcal_day), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.padding(bottom = 4.dp))
                    }
                    Text(text = stringResource(id = R.string.edit_profile_tdee_desc), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f), maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun ProfileFormSection(state: EditProfileState, onEvent: (EditProfileEvent) -> Unit) {
    val dimens = LocalDimens.current
    Surface(
        shape = RoundedCornerShape(dimens.cornerLarge),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                UnderlinedTextField(
                    label = stringResource(id = R.string.label_dob),
                    value = state.age,
                    onValueChange = { onEvent(EditProfileEvent.OnAgeChange(it)) },
                    modifier = Modifier.weight(1f),
                    keyboardType = KeyboardType.Number
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.label_gender),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    SegmentedButton(
                        items = listOf(
                            stringResource(id = R.string.gender_male) to true,
                            stringResource(id = R.string.gender_female) to false
                        ),
                        selectedItem = state.isMale,
                        onItemSelected = { onEvent(EditProfileEvent.OnGenderChange(it)) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UnderlinedTextField(
                    label = stringResource(id = R.string.label_weight),
                    value = state.weight,
                    onValueChange = { onEvent(EditProfileEvent.OnWeightChange(it)) },
                    suffix = stringResource(id = R.string.unit_kg),
                    isError = state.weightError,
                    errorText = stringResource(id = R.string.error_weight_invalid),
                    modifier = Modifier.weight(1f),
                    keyboardType = KeyboardType.Number
                )
                UnderlinedTextField(
                    label = stringResource(id = R.string.label_height),
                    value = state.height,
                    onValueChange = { onEvent(EditProfileEvent.OnHeightChange(it)) },
                    suffix = stringResource(id = R.string.unit_cm),
                    isError = state.heightError,
                    errorText = stringResource(id = R.string.error_height_invalid),
                    modifier = Modifier.weight(1f),
                    keyboardType = KeyboardType.Number
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
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                SegmentedButton(
                    items = listOf(
                        stringResource(id = R.string.goal_value_lose) to stringResource(id = R.string.goal_value_lose),
                        stringResource(id = R.string.goal_value_maintain) to stringResource(id = R.string.goal_value_maintain),
                        stringResource(id = R.string.goal_value_gain) to stringResource(id = R.string.goal_value_gain)
                    ),
                    selectedItem = state.goal,
                    onItemSelected = { onEvent(EditProfileEvent.OnGoalChange(it)) }
                )
            }
        }
    }
}

@Composable
private fun UnderlinedTextField(
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
            modifier = Modifier.padding(bottom = 6.dp)
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
                            width = 1.dp,
                            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(dimens.cornerSmall)
                        )
                        .padding(horizontal = dimens.md, vertical = 12.dp),
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
                        Icon(imageVector = it, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                    suffix?.let {
                        Spacer(modifier = Modifier.width(dimens.sm))
                        Text(text = it, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        )
        if (isError) {
            Text(text = errorText, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
private fun <T> SegmentedButton(items: List<Pair<String, T>>, selectedItem: T, onItemSelected: (T) -> Unit) {
    val dimens = LocalDimens.current
    Surface(
        shape = RoundedCornerShape(dimens.cornerSmall),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        modifier = Modifier.fillMaxWidth().height(44.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { (label, item) ->
                val isSelected = selectedItem == item
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(dimens.cornerSmall - 2.dp))
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
private fun BottomActionArea(isLoading: Boolean, onSaveClick: () -> Unit) {
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
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(dimens.md))
                    Text(text = stringResource(id = R.string.edit_profile_saving), style = MaterialTheme.typography.titleMedium)
                } else {
                    Text(text = stringResource(id = R.string.edit_profile_save_changes), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(dimens.sm))
                    Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}
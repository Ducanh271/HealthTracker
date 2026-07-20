package com.example.healthtracker.ui.features.diary.activity.activityComponents.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.ui.theme.LocalDimens
import kotlin.math.roundToInt

@Composable
fun LogActivityDialog(
    activityItem: ActivityCatalogItem,
    userWeightKg: Float = 70f, // Mặc định 70kg, sau này có thể lấy từ User Profile
    calculateCalories: (metValue: Float, duration: Int) -> Int,
    onDismiss: () -> Unit,
    onConfirm: (durationMinutes: Int, caloriesBurned: Int) -> Unit
) {
    val dimens = LocalDimens.current
    var durationText by remember { mutableStateOf("30") }

    // Parse duration an toàn, tránh lỗi khi người dùng xóa trắng ô nhập
    val durationMinutes = durationText.toIntOrNull() ?: 0

    // SỬA DÒNG NÀY:
    val estimatedCalories = remember(durationMinutes, activityItem.metValue, userWeightKg) { // Dùng durationMinutes thay vì duration
        calculateCalories(activityItem.metValue, durationMinutes) // Dùng durationMinutes ở đây
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Cho phép custom width
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f) // Chiếm 92% chiều rộng màn hình
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(28.dp))
                .padding(dimens.lg)
        ) {
            // 1. Header hiển thị thông tin hoạt động
            ActivityHeader(item = activityItem)

            Spacer(modifier = Modifier.height(dimens.xl))

            Text(
                text = stringResource(id = R.string.activity_log_input_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = dimens.lg)
            )

            // 2. Khu vực nhập thời gian
            DurationInputSection(
                durationText = durationText,
                onDurationChange = { durationText = it },
                onDecrease = {
                    val current = durationText.toIntOrNull() ?: 0
                    durationText = maxOf(0, current - 5).toString()
                },
                onIncrease = {
                    val current = durationText.toIntOrNull() ?: 0
                    durationText = (current + 5).toString()
                }
            )

            Spacer(modifier = Modifier.height(dimens.lg))

            // 3. Hiển thị Calo tính toán
            CaloriesSummarySection(calories = estimatedCalories)

            Spacer(modifier = Modifier.height(dimens.xl))

            // 4. Các nút thao tác
            DialogActionButtons(
                onCancel = onDismiss,
                onConfirm = { onConfirm(durationMinutes, estimatedCalories) }
            )

            // 5. Chú thích MET
            Text(
                text = stringResource(id = R.string.activity_log_met_hint, activityItem.metValue),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.lg, start = dimens.sm, end = dimens.sm)
            )
        }
    }
}

// ================= CÁC COMPONENT CON =================

@Composable
private fun ActivityHeader(item: ActivityCatalogItem) {
    val dimens = LocalDimens.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.width(dimens.lg))
        Column {
            Text(
                text = stringResource(id = R.string.activity_log_selected).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun DurationInputSection(
    durationText: String,
    onDurationChange: (String) -> Unit,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    val dimens = LocalDimens.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(dimens.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.activity_log_duration_label),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = dimens.sm)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.lg)
        ) {
            // Nút Giảm
            IconButton(
                onClick = onDecrease,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
            ) {
                Icon(Icons.Default.Remove, contentDescription = "-", tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }

            // Ô nhập số bằng BasicTextField để giống thiết kế
            Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center) {
                BasicTextField(
                    value = durationText,
                    onValueChange = { newValue ->
                        // Chỉ cho phép nhập số
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d+\$"))) {
                            onDurationChange(newValue)
                        }
                    },
                    textStyle = MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                )
                // Dòng gạch dưới mờ
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape)
                )
            }

            // Nút Tăng
            IconButton(
                onClick = onIncrease,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
            ) {
                Icon(Icons.Default.Add, contentDescription = "+", tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }
    }
}

@Composable
private fun CaloriesSummarySection(calories: Int) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(dimens.lg),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(dimens.sm)) {
            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(
                text = stringResource(id = R.string.activity_log_estimated_cals),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(dimens.xs)) {
            Text(
                text = calories.toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(id = R.string.activity_unit_kcal),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DialogActionButtons(onCancel: () -> Unit, onConfirm: () -> Unit) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.weight(1f).height(48.dp),
            shape = CircleShape
        ) {
            Text(stringResource(id = R.string.activity_action_cancel), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Button(
            onClick = onConfirm,
            modifier = Modifier.weight(1f).height(48.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(id = R.string.activity_log_confirm))
        }
    }
}
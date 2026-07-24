package com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.components.LocalizedContent
import com.example.healthtracker.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewFoodBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (name: String, portion: String, calories: Int, quantity: Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    var foodName by remember { mutableStateOf("") }
    var portion by remember { mutableStateOf("") }
    var caloriesStr by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(1) }

    val isValid = foodName.isNotBlank() && portion.isNotBlank() && caloriesStr.isNotBlank()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)) }
    ) {
        LocalizedContent {
            Column(
                modifier = Modifier.fillMaxHeight(0.9f)
            ) {
                AddNewFoodHeader(onClose = onDismiss)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(horizontal = dimens.lg, vertical = dimens.md),
                    verticalArrangement = Arrangement.spacedBy(dimens.lg)
                ) {
                    FoodBannerImage()

                    FoodInputForm(
                        foodName = foodName, onNameChange = { foodName = it },
                        portion = portion, onPortionChange = { portion = it },
                        calories = caloriesStr, onCaloriesChange = {
                            if (it.isEmpty() || it.matches(Regex("^\\d+\$"))) caloriesStr = it
                        },
                        quantity = quantity,
                        onDecrease = { if (quantity > 1) quantity-- },
                        onIncrease = { quantity++ }
                    )

                    QuickSuggestions(
                        onSuggestionClick = { name, port, cal ->
                            foodName = name
                            portion = port
                            caloriesStr = cal.toString()
                        }
                    )
                }

                AddNewFoodFooter(
                    isValid = isValid,
                    onCancel = onDismiss,
                    onSubmit = {
                        val cal = caloriesStr.toIntOrNull() ?: 0
                        onSubmit(foodName.trim(), portion.trim(), cal, quantity)
                    }
                )
            }
        }
    }
}

@Composable
private fun AddNewFoodHeader(onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalDimens.current.lg, vertical = LocalDimens.current.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.add_new_title),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh, CircleShape)
                .size(40.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun FoodBannerImage() {
    val dimens = LocalDimens.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        )
        Icon(
            imageVector = Icons.Default.Restaurant,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun FoodInputForm(
    foodName: String, onNameChange: (String) -> Unit,
    portion: String, onPortionChange: (String) -> Unit,
    calories: String, onCaloriesChange: (String) -> Unit,
    quantity: Int, onDecrease: () -> Unit, onIncrease: () -> Unit
) {
    val dimens = LocalDimens.current

    Column(verticalArrangement = Arrangement.spacedBy(dimens.md)) {
        CustomInputField(
            label = stringResource(id = R.string.add_new_name),
            value = foodName,
            onValueChange = onNameChange,
            placeholder = stringResource(id = R.string.add_new_name_hint)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(dimens.md)) {
            Box(modifier = Modifier.weight(1f)) {
                CustomInputField(
                    label = stringResource(id = R.string.add_new_portion),
                    value = portion,
                    onValueChange = onPortionChange,
                    placeholder = stringResource(id = R.string.add_new_portion_hint),
                    trailingIcon = { Icon(Icons.Default.Restaurant, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomInputField(
                    label = stringResource(id = R.string.add_new_calories),
                    value = calories,
                    onValueChange = onCaloriesChange,
                    placeholder = "0",
                    keyboardType = KeyboardType.Number,
                    trailingIcon = { Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(dimens.xs)) {
            Text(
                text = stringResource(id = R.string.add_new_quantity),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = dimens.xs)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDecrease, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Remove, contentDescription = "-", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = onIncrease, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Add, contentDescription = "+", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val dimens = LocalDimens.current
    Column(verticalArrangement = Arrangement.spacedBy(dimens.xs)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = dimens.xs)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) },
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuickSuggestions(onSuggestionClick: (String, String, Int) -> Unit) {
    val dimens = LocalDimens.current
    val suggestions = listOf(
        Triple("Cơm tấm", "1 đĩa", 500),
        Triple("Bún chả", "1 suất", 450),
        Triple("Salad ức gà", "1 bát", 250)
    )

    Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
        Text(
            text = stringResource(id = R.string.add_new_suggestions_title),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = dimens.xs)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(dimens.sm),
            verticalArrangement = Arrangement.spacedBy(dimens.sm)
        ) {
            suggestions.forEach { (name, portion, cal) ->
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), CircleShape)
                        .clickable { onSuggestionClick(name, portion, cal) }
                        .padding(horizontal = dimens.md, vertical = dimens.sm)
                ) {
                    Text(text = name, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun AddNewFoodFooter(isValid: Boolean, onCancel: () -> Unit, onSubmit: () -> Unit) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(horizontal = dimens.lg, vertical = dimens.lg)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        horizontalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.weight(1f).height(50.dp),
            shape = CircleShape,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Text(stringResource(id = R.string.sheet_action_cancel), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
        }

        Button(
            onClick = onSubmit,
            enabled = isValid,
            modifier = Modifier.weight(2f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = CircleShape
        ) {
            Text(stringResource(id = R.string.add_new_submit), style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(dimens.sm))
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
        }
    }
}
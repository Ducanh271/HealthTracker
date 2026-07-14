package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLevelSelector(
    activityLevel: Int,
    onLevelChange: (Int) -> Unit
) {
    val dimens = LocalDimens.current
    var expanded by remember { mutableStateOf(false) }

    val activityOptions = listOf(
        stringResource(id = R.string.activity_level_1),
        stringResource(id = R.string.activity_level_2),
        stringResource(id = R.string.activity_level_3),
        stringResource(id = R.string.activity_level_4),
        stringResource(id = R.string.activity_level_5)
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = activityOptions[activityLevel - 1],
            onValueChange = { },
            label = { Text(stringResource(id = R.string.label_activity_level)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            shape = RoundedCornerShape(dimens.cornerMedium),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            activityOptions.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onLevelChange(index + 1)
                        expanded = false
                    }
                )
            }
        }
    }
}
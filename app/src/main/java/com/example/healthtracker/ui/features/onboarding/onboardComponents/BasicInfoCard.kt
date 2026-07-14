package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun BasicInfoCard(
    name: String, onNameChange: (String) -> Unit,
    dob: String, onDobChange: (String) -> Unit,
    isMale: Boolean, onGenderChange: (Boolean) -> Unit
) {
    val dimens = LocalDimens.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(dimens.cornerLarge))
            .padding(dimens.md),
        verticalArrangement = Arrangement.spacedBy(dimens.md)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(id = R.string.label_fullname)) },
            placeholder = { Text(stringResource(id = R.string.placeholder_fullname)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimens.cornerMedium)
        )

        OutlinedTextField(
            value = dob,
            onValueChange = onDobChange,
            label = { Text(stringResource(id = R.string.label_dob)) },
            placeholder = { Text(stringResource(id = R.string.placeholder_dob)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimens.cornerMedium)
        )

        Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
            Text(
                text = stringResource(id = R.string.label_gender),
                style = MaterialTheme.typography.labelLarge
            )
            GenderSelector(isMale = isMale, onGenderChange = onGenderChange)
        }
    }
}
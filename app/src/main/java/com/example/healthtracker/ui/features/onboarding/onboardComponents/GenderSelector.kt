package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun GenderSelector(isMale: Boolean, onGenderChange: (Boolean) -> Unit) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(dimens.xs)
    ) {
        GenderButton(
            text = stringResource(id = R.string.gender_male),
            isSelected = isMale,
            onClick = { onGenderChange(true) }
        )
        GenderButton(
            text = stringResource(id = R.string.gender_female),
            isSelected = !isMale,
            onClick = { onGenderChange(false) }
        )
    }
}

@Composable
private fun GenderButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val dimens = LocalDimens.current
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = dimens.lg , vertical = dimens.sm)
    ) {
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
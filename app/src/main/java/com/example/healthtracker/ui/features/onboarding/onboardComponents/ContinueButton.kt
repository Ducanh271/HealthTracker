package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun ContinueButton(isLoading: Boolean, onClick: () -> Unit) {
    val dimens = LocalDimens.current
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = CircleShape,
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = stringResource(id = R.string.btn_continue),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.width(dimens.sm))
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}
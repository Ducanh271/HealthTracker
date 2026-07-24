package com.example.healthtracker.ui.features.profile.editProfileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun AvatarSection() {
    val dimens = LocalDimens.current
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(dimens.avatarSize)
                .clip(CircleShape)
                .border(dimens.strokeWidth, MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(dimens.avatarSize / 2),
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}
package com.example.healthtracker.ui.features.onboarding.onboardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.features.onboarding.OnboardingState
import com.example.healthtracker.ui.theme.LocalDimens


@Composable
fun GoalSelectionSection(
    currentGoal: String,
    onGoalChange: (String) -> Unit
) {
    val dimens = LocalDimens.current
    Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
        Text(
            text = stringResource(id = R.string.label_goal),
            style = MaterialTheme.typography.labelLarge
        )
        // Do data fix cứng nên em không để động, nếu là lấy dữ liệu từ backend thì em sẽ dùng for để duyệt list
        GoalCard(
            title = stringResource(id = R.string.goal_lose_title),
            desc = stringResource(id = R.string.goal_lose_desc),
            icon = Icons.Default.LocalFireDepartment,
            isSelected = currentGoal == OnboardingState.GOAL_LOSE,
            onClick = { onGoalChange(OnboardingState.GOAL_LOSE) }
        )
        GoalCard(
            title = stringResource(id = R.string.goal_maintain_title),
            desc = stringResource(id = R.string.goal_maintain_desc),
            icon = Icons.Default.Favorite,
            isSelected = currentGoal == OnboardingState.GOAL_MAINTAIN,
            onClick = { onGoalChange(OnboardingState.GOAL_MAINTAIN) }
        )
        GoalCard(
            title = stringResource(id = R.string.goal_gain_title),
            desc = stringResource(id = R.string.goal_gain_desc),
            icon = Icons.Default.FitnessCenter,
            isSelected = currentGoal == OnboardingState.GOAL_GAIN,
            onClick = { onGoalChange(OnboardingState.GOAL_GAIN) }
        )
    }
}

@Composable
private fun GoalCard(
    title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean, onClick: () -> Unit
) {
    val dimens = LocalDimens.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.cornerLarge))
            .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(dimens.cornerLarge)
            )
            .clickable { onClick() }
            .padding(dimens.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(dimens.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
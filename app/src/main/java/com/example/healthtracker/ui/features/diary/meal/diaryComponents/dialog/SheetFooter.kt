package com.example.healthtracker.ui.features.diary.meal.diaryComponents.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun SheetFooter(
    hasItems: Boolean,
    onAddNewFood: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    val dimens = LocalDimens.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(dimens.marginMobile)
    ) {
        OutlinedButton(
            onClick = onAddNewFood,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(dimens.cornerLarge),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Icon(Icons.Default.AddBox, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(dimens.sm))
            Text(stringResource(id = R.string.sheet_action_add_new), color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(dimens.sm))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(dimens.sm)) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(dimens.cornerLarge)
            ) {
                Text(stringResource(id = R.string.sheet_action_cancel))
            }

            Button(
                onClick = onConfirm,
                enabled = hasItems,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(dimens.cornerLarge)
            ) {
                Text(stringResource(id = R.string.sheet_action_confirm))
            }
        }
    }
}
package com.example.healthtracker.ui.features.dashboard.dashboardComponents


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtracker.R
import com.example.healthtracker.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    currentDate: String,
    onProfileClick: () -> Unit
) {
    val dimens = LocalDimens.current
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.dash_date_today, currentDate),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onSurface)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardTopBarPreview() {
    MaterialTheme {
        DashboardTopBar(currentDate = "24/05/2026", onProfileClick = {})
    }
}
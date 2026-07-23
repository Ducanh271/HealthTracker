package com.example.healthtracker.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.AdviceType
import com.example.healthtracker.ui.features.dashboard.dashboardComponents.*
import com.example.healthtracker.ui.theme.LocalDimens

@Composable
fun DashboardScreen(
    onProfileClick: () -> Unit,
    onAddActivityClick: () -> Unit,
    onAddMealClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    DashboardContent(
        state = state,
        onProfileClick = onProfileClick,
        onAddActivityClick = onAddActivityClick,
        onAddMealClick = onAddMealClick
    )
}


@Composable
fun DashboardContent(
    state: DashboardState,
    onProfileClick: () -> Unit,
    onAddActivityClick: () -> Unit,
    onAddMealClick: () -> Unit
) {
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()
    val adviceStringRes = when (state.adviceType) {
        AdviceType.START_DAY -> R.string.advice_start_day
        AdviceType.EXCEEDED -> R.string.advice_exceeded
        AdviceType.ALMOST_THERE -> R.string.advice_almost_there
        AdviceType.KEEP_GOING -> R.string.advice_keep_going
    }

    Scaffold(
        topBar = {
            DashboardTopBar(
                currentDate = state.currentDate,
                onProfileClick = onProfileClick
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(dimens.sm)
            ) {
                SmallFloatingActionButton(
                    onClick = onAddActivityClick,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = dimens.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(dimens.xs))
                        Text(stringResource(id = R.string.dash_fab_activity))
                    }
                }
                ExtendedFloatingActionButton(
                    onClick = onAddMealClick,
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text(stringResource(id = R.string.dash_fab_meal)) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.marginMobile)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(dimens.lg),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(dimens.xs))

                ProgressCircle(
                    consumed = state.consumedCalories,
                    target = state.targetCalories,
                    burned = state.burnedCalories,
                    remaining = state.remainingCalories
                )

                SummaryGrid(
                    consumed = state.consumedCalories,
                    burned = state.burnedCalories,
                    balance = state.balanceCalories
                )

                AdviceBanner(adviceText = stringResource(id = adviceStringRes))

                WeeklyBarChart(weeklyData = state.weeklyBarData)
                WeeklyLineChart(weeklyData = state.weeklyLineData)
                WeeklyStatsRow(stats = state.stats)
                Spacer(modifier = Modifier.height(dimens.fabClearance))
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun DashboardScreenPreview() {
    com.example.healthtracker.ui.theme.HealthTrackerTheme {
        DashboardContent(
            state = DashboardState(),
            onProfileClick = {},
            onAddActivityClick = {},
            onAddMealClick = {}
        )
    }
}
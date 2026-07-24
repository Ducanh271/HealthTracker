package com.example.healthtracker.ui.features.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardContent(
        state = state,
        onProfileClick = onProfileClick,
        onAddActivityClick = onAddActivityClick,
        onAddMealClick = onAddMealClick,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun DashboardContent(
    state: DashboardState,
    onProfileClick: () -> Unit,
    onAddActivityClick: () -> Unit,
    onAddMealClick: () -> Unit,
    onEvent: (DashboardEvent) -> Unit = {}
) {
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    var isScrollingDown by remember { mutableStateOf(false) }
    LaunchedEffect(scrollState) {
        var previous = scrollState.value
        snapshotFlow { scrollState.value }.collect { current ->
            isScrollingDown = current > previous && current > 0
            previous = current
        }
    }
    LaunchedEffect(scrollState.isScrollInProgress) {
        if (!scrollState.isScrollInProgress) isScrollingDown = false
    }
    val fabAlpha by animateFloatAsState(
        targetValue = if (isScrollingDown) FAB_DIMMED_ALPHA else 1f,
        label = "fabAlpha"
    )

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
                modifier = Modifier.alpha(fabAlpha),
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

                WeeklyBarChart(
                    weeklyData = state.weeklyBarData,
                    weekOffset = state.barWeekOffset,
                    onPreviousWeek = { onEvent(DashboardEvent.PreviousBarWeek) },
                    onNextWeek = { onEvent(DashboardEvent.NextBarWeek) }
                )
                WeeklyLineChart(
                    weeklyData = state.weeklyLineData,
                    weekOffset = state.trendWeekOffset,
                    onPreviousWeek = { onEvent(DashboardEvent.PreviousTrendWeek) },
                    onNextWeek = { onEvent(DashboardEvent.NextTrendWeek) }
                )
                WeeklyStatsRow(stats = state.stats)
                Spacer(modifier = Modifier.height(dimens.fabClearance))
            }
        }
    }
}

private const val FAB_DIMMED_ALPHA = 0.25f

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
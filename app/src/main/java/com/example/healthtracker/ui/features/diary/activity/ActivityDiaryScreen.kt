package com.example.healthtracker.ui.features.diary.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.ActivityCatalogItem
import com.example.healthtracker.domain.model.ActivityItem
import com.example.healthtracker.domain.model.ActivitySuggestion
import com.example.healthtracker.domain.model.ActivityType
import com.example.healthtracker.domain.model.toCatalogItem
import com.example.healthtracker.ui.features.diary.activity.activityComponents.ActivityListSection
import com.example.healthtracker.ui.features.diary.activity.activityComponents.ActivitySummaryCard
import com.example.healthtracker.ui.features.diary.activity.activityComponents.QuickSuggestionsSection
import com.example.healthtracker.ui.features.diary.activity.activityComponents.dialog.LogActivityDialog
import com.example.healthtracker.ui.features.diary.meal.diaryComponents.DateSelectorBar
import com.example.healthtracker.ui.features.diary.activity.activityComponents.dialog.SearchActivityBottomSheet
import com.example.healthtracker.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDiaryScreen(
    viewModel: ActivityDiaryViewModel = hiltViewModel()
) {
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    var selectedActivityId by remember { mutableStateOf<Int?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var activityToEdit by remember { mutableStateOf<ActivityItem?>(null) }
    var showSearchSheet by remember { mutableStateOf(false) }
    var selectedActivityToLog by remember { mutableStateOf<ActivityCatalogItem?>(null) }

    val suggestions = remember {
        listOf(
            ActivitySuggestion("Đi bộ", 3.5f, ActivityType.WALKING),
            ActivitySuggestion("Chạy bộ", 8.0f, ActivityType.CARDIO),
            ActivitySuggestion("Bơi lội", 7.0f, ActivityType.SWIMMING)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.activity_diary_title), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSearchSheet = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Activity")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = dimens.marginMobile)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(dimens.lg)
        ) {
            DateSelectorBar(
                selectedDate = state.selectedDate,
                onDateSelected = { viewModel.updateSelectedDate(it) }
            )

            val progressPercent = if (state.targetBurnCalories > 0) {
                ((state.totalBurnedCalories.toFloat() / state.targetBurnCalories) * 100).toInt()
            } else 0

            ActivitySummaryCard(
                totalBurned = state.totalBurnedCalories,
                progressPercent = progressPercent
            )

            QuickSuggestionsSection(
                suggestions = suggestions,
                onClick = { /* TODO: Xử lý click gợi ý nhanh */ }
            )

            ActivityListSection(
                activities = state.activities,
                onItemClick = { item -> selectedActivityId = if(selectedActivityId == item.id) null else item.id },
                selectedId = selectedActivityId,
                onDelete = { showDeleteDialog = true },
                onEdit = {
                    activityToEdit = it
                    selectedActivityToLog = it.toCatalogItem()
                    showEditDialog = true
                }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }


    if (showSearchSheet) {
        SearchActivityBottomSheet(
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            searchResults = searchResults,
            onDismiss = {
                showSearchSheet = false
                viewModel.updateSearchQuery("")
            },
            onAddManualClick = {
                showSearchSheet = false
                // TODO: Bật một state khác để mở form thêm thủ công
            },
            onActivitySelect = { selectedActivity ->
                showSearchSheet = false
                viewModel.updateSearchQuery("")
                selectedActivityToLog = selectedActivity
            }
        )
    }

    selectedActivityToLog?.let { activity ->
        LogActivityDialog(
            activityItem = activity,
            calculateCalories = { met, duration ->
                viewModel.calculateCalories(metValue = met, durationMinutes = duration)
            },
            onDismiss = { selectedActivityToLog = null },
            onConfirm = { duration, calories ->
                viewModel.addActivityLog(
                    name = activity.name,
                    durationMinutes = duration,
                    caloriesBurned = calories
                )

                selectedActivityToLog = null
            }
        )
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xóa hoạt động") },
            text = { Text("Bạn có chắc muốn xóa hoạt động này không?") },
            confirmButton = {
                TextButton(onClick = {
                    selectedActivityId?.let { id ->
                        viewModel.deleteActivityLog(id)
                    }
                    showDeleteDialog = false
                    selectedActivityId = null
                }) { Text("Xóa", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Hủy") } }
        )
    }

    if (showEditDialog && activityToEdit != null) {
        LogActivityDialog(
            activityItem = selectedActivityToLog!!,
            calculateCalories = { met, duration ->
                viewModel.calculateCalories(metValue = met, durationMinutes = duration)
            },
            onDismiss = { showEditDialog = false },
            onConfirm = { duration, calories ->
                viewModel.updateActivityLog(activityToEdit!!.id, duration, calories)
                showEditDialog = false
                activityToEdit = null
            }
        )
    }
}
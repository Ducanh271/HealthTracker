package com.example.healthtracker.ui.features.onboarding

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthtracker.ui.features.onboarding.onboardComponents.*
import com.example.healthtracker.ui.theme.LocalDimens
import androidx.compose.runtime.LaunchedEffect
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit = {}
) {
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.isNavigateToDashboard) {
        Log.d("OnboardingFlow", "TRẠM 5: LaunchedEffect check, isNavigate = ${state.isNavigateToDashboard}")
        if (state.isNavigateToDashboard) {
            Log.d("OnboardingFlow", "TRẠM 6: Gọi onNavigateToDashboard()")
            onNavigateToDashboard()
            viewModel.resetNavigationFlag()
        }
    }

    Scaffold(
        topBar = { OnboardingTopBar() }
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
            Spacer(modifier = Modifier.height(dimens.md))

            WelcomeSection()

            BasicInfoCard(
                name = state.name, onNameChange = viewModel::updateName, nameError = state.nameError,
                dob = state.dob, onDobChange = viewModel::updateDob, dobError = state.dobError,
                isMale = state.isMale, onGenderChange = viewModel::updateGender
            )

            MetricsSection(
                weight = state.weight, onWeightChange = viewModel::updateWeight, weightError = state.weightError as Int?,
                height = state.height, onHeightChange = viewModel::updateHeight, heightError = state.heightError as Int?
            )

            ActivityLevelSelector(
                activityLevel = state.activityLevel,
                onLevelChange = viewModel::updateActivityLevel
            )

            GoalSelectionSection(
                currentGoal = state.goal,
                onGoalChange = viewModel::updateGoal
            )

            ContinueButton(
                isLoading = state.isLoading,
                onClick = { viewModel.completeOnboarding() }
            )

            FooterInfo()

            Spacer(modifier = Modifier.height(dimens.xl))
        }
    }
}
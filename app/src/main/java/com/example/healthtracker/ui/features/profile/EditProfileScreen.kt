package com.example.healthtracker.ui.features.profile

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthtracker.R
import com.example.healthtracker.ui.features.profile.editProfileComponents.*
import com.example.healthtracker.ui.theme.LocalDimens
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dimens = LocalDimens.current
    val scrollState = rememberScrollState()

    LaunchedEffect(state.showSuccessToast) {
        if (state.showSuccessToast) {
            delay(1500)
            viewModel.onEvent(EditProfileEvent.OnToastDismiss)
            onNavigateBack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.edit_profile_title),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.action_back),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                )
            },
            bottomBar = {
                BottomActionArea(
                    isLoading = state.isLoading,
                    onSaveClick = { viewModel.onEvent(EditProfileEvent.OnSaveClick) }
                )
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
                Spacer(modifier = Modifier.height(dimens.sm))

                AvatarSection()

                BentoMetricsSection(bmiValue = state.bmiValue, bmiCategory = state.bmiCategory, tdee = state.tdeeValue)

                ProfileFormSection(state = state, onEvent = viewModel::onEvent)

                Spacer(modifier = Modifier.height(dimens.xl * 4))
            }
        }

        // Toast Notification
        AnimatedVisibility(
            visible = state.showSuccessToast,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = dimens.xl * 2)
                .zIndex(60f)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(dimens.cornerFull),
                shadowElevation = dimens.sm
            ) {
                Text(
                    text = stringResource(id = R.string.edit_profile_save_success),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.padding(horizontal = dimens.lg, vertical = dimens.sm),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
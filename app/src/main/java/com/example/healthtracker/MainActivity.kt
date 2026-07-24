package com.example.healthtracker

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.healthtracker.domain.repository.UserRepository
import com.example.healthtracker.notification.ReminderScheduler
import com.example.healthtracker.ui.theme.AppFontSize
import com.example.healthtracker.ui.navigation.AppNavGraph
import com.example.healthtracker.ui.navigation.Screen
import com.example.healthtracker.ui.theme.AppThemeType
import com.example.healthtracker.ui.theme.HealthTrackerTheme
import com.example.healthtracker.widget.WidgetUpdater
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var reminderScheduler: ReminderScheduler

    @Inject
    lateinit var widgetUpdater: WidgetUpdater

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestNotificationPermissionIfNeeded()
        lifecycleScope.launch { reminderScheduler.refreshDailyReminders() }

        setContent {
            val themeStr by userRepository.appTheme.collectAsState(initial = AppThemeType.DEFAULT.name)
            val modeStr by userRepository.appMode.collectAsState(initial = "SYSTEM")
            val fontSizeStr by userRepository.fontSize.collectAsState(initial = AppFontSize.MEDIUM.name)

            val languageStr by userRepository.appLanguage.collectAsState(initial = "vi")

            val mainViewModel: MainViewModel = hiltViewModel()
            val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()


            val context = LocalContext.current
            val currentConfig = LocalConfiguration.current

            val locale = Locale.forLanguageTag(languageStr.lowercase())
            Locale.setDefault(locale)

            val configuration = Configuration(currentConfig)
            configuration.setLocale(locale)


            val localizedContext = remember(context, locale) {
                val configContext = context.createConfigurationContext(configuration)
                object : android.content.ContextWrapper(context) {
                    override fun getResources(): android.content.res.Resources {
                        return configContext.resources
                    }
                }
            }

            val currentTheme = remember(themeStr) {
                try { AppThemeType.valueOf(themeStr) } catch (e: Exception) { AppThemeType.DEFAULT }
            }

            val currentFontSize = remember(fontSizeStr) {
                try { AppFontSize.valueOf(fontSizeStr) } catch (e: Exception) { AppFontSize.MEDIUM }
            }

            val isSystemDark = isSystemInDarkTheme()
            val darkTheme = remember(modeStr, isSystemDark) {
                when (modeStr) {
                    "LIGHT" -> false
                    "DARK" -> true
                    else -> isSystemDark
                }
            }

            CompositionLocalProvider(
                LocalContext provides localizedContext,
                LocalConfiguration provides configuration
            ) {
                HealthTrackerTheme(
                    darkTheme = darkTheme,
                    themeType = currentTheme,
                    fontSize = currentFontSize
                ) {
                    if (isOnboardingCompleted != null) {
                        val startRoute = if (isOnboardingCompleted == true) {
                            Screen.Dashboard.route
                        } else {
                            Screen.Onboarding.route
                        }
                        AppNavGraph(startDestination = startRoute)
                    } else {
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        widgetUpdater.refresh()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
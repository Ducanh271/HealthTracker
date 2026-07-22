package com.example.healthtracker.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.healthtracker.ui.features.settings.AppFontSize
import com.example.healthtracker.R
enum class AppThemeType(
    val color: Color,            // Màu sắc hiển thị trực quan (màu thật)
    val stringResId: Int         // ID của string.xml tương ứng
) {
    DEFAULT(
        color = Color(0xFF6750A4),
        stringResId = R.string.theme_classic_purple
    ),
    VIBRANT_ENERGY(
        color = Color(0xFFA73B00),
        stringResId = R.string.theme_energy_orange
    ),
    SERENE_OCEAN(
        color = Color(0xFF00535B),
        stringResId = R.string.theme_deep_ocean
    ),
    NATURE_VITALITY(
        color = Color(0xFF4A654A),
        stringResId = R.string.theme_soft_berry
    )
}
// =========================================
// 1. CLASSIC PURPLE (DEFAULT)
// =========================================
private val DefaultLightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    inversePrimary = InversePrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    surfaceTint = SurfaceTintLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    surfaceBright = SurfaceBrightLight,
    surfaceDim = SurfaceDimLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight
)

private val DefaultDarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    inversePrimary = InversePrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceTint = SurfaceTintDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    surfaceBright = SurfaceBrightDark,
    surfaceDim = SurfaceDimDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark
)

// =========================================
// 2. SERENE OCEAN (ĐẠI DƯƠNG SÂU)
// =========================================
private val SereneOceanLightColorScheme = lightColorScheme(
    primary = PrimaryOceanLight,
    onPrimary = OnPrimaryOceanLight,
    primaryContainer = PrimaryContainerOceanLight,
    onPrimaryContainer = OnPrimaryContainerOceanLight,
    secondary = SecondaryOceanLight,
    onSecondary = OnSecondaryOceanLight,
    secondaryContainer = SecondaryContainerOceanLight,
    onSecondaryContainer = OnSecondaryContainerOceanLight,
    tertiary = TertiaryOceanLight,
    onTertiary = OnTertiaryOceanLight,
    tertiaryContainer = TertiaryContainerOceanLight,
    onTertiaryContainer = OnTertiaryContainerOceanLight,
    error = ErrorOceanLight,
    onError = OnErrorOceanLight,
    errorContainer = ErrorContainerOceanLight,
    onErrorContainer = OnErrorContainerOceanLight,
    background = BackgroundOceanLight,
    onBackground = OnBackgroundOceanLight,
    surface = SurfaceOceanLight,
    onSurface = OnSurfaceOceanLight,
    surfaceVariant = SurfaceVariantOceanLight,
    onSurfaceVariant = OnSurfaceVariantOceanLight,
    surfaceContainerLowest = SurfaceContainerLowestOceanLight,
    surfaceContainerLow = SurfaceContainerLowOceanLight,
    surfaceContainer = SurfaceContainerOceanLight,
    surfaceContainerHigh = SurfaceContainerHighOceanLight,
    surfaceContainerHighest = SurfaceContainerHighestOceanLight,
    outline = OutlineOceanLight,
    outlineVariant = OutlineVariantOceanLight
)

private val SereneOceanDarkColorScheme = darkColorScheme(
    primary = PrimaryOceanDark,
    onPrimary = OnPrimaryOceanDark,
    primaryContainer = PrimaryContainerOceanDark,
    onPrimaryContainer = OnPrimaryContainerOceanDark,
    secondary = SecondaryOceanDark,
    onSecondary = OnSecondaryOceanDark,
    secondaryContainer = SecondaryContainerOceanDark,
    onSecondaryContainer = OnSecondaryContainerOceanDark,
    tertiary = TertiaryOceanDark,
    onTertiary = OnTertiaryOceanDark,
    tertiaryContainer = TertiaryContainerOceanDark,
    onTertiaryContainer = OnTertiaryContainerOceanDark,
    error = ErrorOceanDark,
    onError = OnErrorOceanDark,
    errorContainer = ErrorContainerOceanDark,
    onErrorContainer = OnErrorContainerOceanDark,
    background = BackgroundOceanDark,
    onBackground = OnBackgroundOceanDark,
    surface = SurfaceOceanDark,
    onSurface = OnSurfaceOceanDark,
    surfaceVariant = SurfaceVariantOceanDark,
    onSurfaceVariant = OnSurfaceVariantOceanDark,
    surfaceContainerLowest = SurfaceContainerLowestOceanDark,
    surfaceContainerLow = SurfaceContainerLowOceanDark,
    surfaceContainer = SurfaceContainerOceanDark,
    surfaceContainerHigh = SurfaceContainerHighOceanDark,
    surfaceContainerHighest = SurfaceContainerHighestOceanDark,
    outline = OutlineOceanDark,
    outlineVariant = OutlineVariantOceanDark
)

// =========================================
// 3. VIBRANT ENERGY (XANH / CAM NĂNG LƯỢNG)
// =========================================
private val VibrantEnergyLightColorScheme = lightColorScheme(
    primary = PrimaryEnergyLight,
    onPrimary = OnPrimaryEnergyLight,
    primaryContainer = PrimaryContainerEnergyLight,
    onPrimaryContainer = OnPrimaryContainerEnergyLight,
    secondary = SecondaryEnergyLight,
    onSecondary = OnSecondaryEnergyLight,
    secondaryContainer = SecondaryContainerEnergyLight,
    onSecondaryContainer = OnSecondaryContainerEnergyLight,
    tertiary = TertiaryEnergyLight,
    onTertiary = OnTertiaryEnergyLight,
    tertiaryContainer = TertiaryContainerEnergyLight,
    onTertiaryContainer = OnTertiaryContainerEnergyLight,
    error = ErrorEnergyLight,
    onError = OnErrorEnergyLight,
    errorContainer = ErrorContainerEnergyLight,
    onErrorContainer = OnErrorContainerEnergyLight,
    background = BackgroundEnergyLight,
    onBackground = OnBackgroundEnergyLight,
    surface = SurfaceEnergyLight,
    onSurface = OnSurfaceEnergyLight,
    surfaceVariant = SurfaceVariantEnergyLight,
    onSurfaceVariant = OnSurfaceVariantEnergyLight,
    surfaceContainerLowest = SurfaceContainerLowestEnergyLight,
    surfaceContainerLow = SurfaceContainerLowEnergyLight,
    surfaceContainer = SurfaceContainerEnergyLight,
    surfaceContainerHigh = SurfaceContainerHighEnergyLight,
    surfaceContainerHighest = SurfaceContainerHighestEnergyLight,
    outline = OutlineEnergyLight,
    outlineVariant = OutlineVariantEnergyLight
)

private val VibrantEnergyDarkColorScheme = darkColorScheme(
    primary = PrimaryEnergyDark,
    onPrimary = OnPrimaryEnergyDark,
    primaryContainer = PrimaryContainerEnergyDark,
    onPrimaryContainer = OnPrimaryContainerEnergyDark,
    secondary = SecondaryEnergyDark,
    onSecondary = OnSecondaryEnergyDark,
    secondaryContainer = SecondaryContainerEnergyDark,
    onSecondaryContainer = OnSecondaryContainerEnergyDark,
    tertiary = TertiaryEnergyDark,
    onTertiary = OnTertiaryEnergyDark,
    tertiaryContainer = TertiaryContainerEnergyDark,
    onTertiaryContainer = OnTertiaryContainerEnergyDark,
    error = ErrorEnergyDark,
    onError = OnErrorEnergyDark,
    errorContainer = ErrorContainerEnergyDark,
    onErrorContainer = OnErrorContainerEnergyDark,
    background = BackgroundEnergyDark,
    onBackground = OnBackgroundEnergyDark,
    surface = SurfaceEnergyDark,
    onSurface = OnSurfaceEnergyDark,
    surfaceVariant = SurfaceVariantEnergyDark,
    onSurfaceVariant = OnSurfaceVariantEnergyDark,
    surfaceContainerLowest = SurfaceContainerLowestEnergyDark,
    surfaceContainerLow = SurfaceContainerLowEnergyDark,
    surfaceContainer = SurfaceContainerEnergyDark,
    surfaceContainerHigh = SurfaceContainerHighEnergyDark,
    surfaceContainerHighest = SurfaceContainerHighestEnergyDark,
    outline = OutlineEnergyDark,
    outlineVariant = OutlineVariantEnergyDark
)

// =========================================
// 4. NATURE VITALITY (THIÊN NHIÊN)
// =========================================
private val NatureVitalityLightColorScheme = lightColorScheme(
    primary = PrimaryNatureLight,
    onPrimary = OnPrimaryNatureLight,
    primaryContainer = PrimaryContainerNatureLight,
    onPrimaryContainer = OnPrimaryContainerNatureLight,
    secondary = SecondaryNatureLight,
    onSecondary = OnSecondaryNatureLight,
    secondaryContainer = SecondaryContainerNatureLight,
    onSecondaryContainer = OnSecondaryContainerNatureLight,
    tertiary = TertiaryNatureLight,
    onTertiary = OnTertiaryNatureLight,
    tertiaryContainer = TertiaryContainerNatureLight,
    onTertiaryContainer = OnTertiaryContainerNatureLight,
    error = ErrorNatureLight,
    onError = OnErrorNatureLight,
    errorContainer = ErrorContainerNatureLight,
    onErrorContainer = OnErrorContainerNatureLight,
    background = BackgroundNatureLight,
    onBackground = OnBackgroundNatureLight,
    surface = SurfaceNatureLight,
    onSurface = OnSurfaceNatureLight,
    surfaceVariant = SurfaceVariantNatureLight,
    onSurfaceVariant = OnSurfaceVariantNatureLight,
    surfaceContainerLowest = SurfaceContainerLowestNatureLight,
    surfaceContainerLow = SurfaceContainerLowNatureLight,
    surfaceContainer = SurfaceContainerNatureLight,
    surfaceContainerHigh = SurfaceContainerHighNatureLight,
    surfaceContainerHighest = SurfaceContainerHighestNatureLight,
    outline = OutlineNatureLight,
    outlineVariant = OutlineVariantNatureLight
)

private val NatureVitalityDarkColorScheme = darkColorScheme(
    primary = PrimaryNatureDark,
    onPrimary = OnPrimaryNatureDark,
    primaryContainer = PrimaryContainerNatureDark,
    onPrimaryContainer = OnPrimaryContainerNatureDark,
    secondary = SecondaryNatureDark,
    onSecondary = OnSecondaryNatureDark,
    secondaryContainer = SecondaryContainerNatureDark,
    onSecondaryContainer = OnSecondaryContainerNatureDark,
    tertiary = TertiaryNatureDark,
    onTertiary = OnTertiaryNatureDark,
    tertiaryContainer = TertiaryContainerNatureDark,
    onTertiaryContainer = OnTertiaryContainerNatureDark,
    error = ErrorNatureDark,
    onError = OnErrorNatureDark,
    errorContainer = ErrorContainerNatureDark,
    onErrorContainer = OnErrorContainerNatureDark,
    background = BackgroundNatureDark,
    onBackground = OnBackgroundNatureDark,
    surface = SurfaceNatureDark,
    onSurface = OnSurfaceNatureDark,
    surfaceVariant = SurfaceVariantNatureDark,
    onSurfaceVariant = OnSurfaceVariantNatureDark,
    surfaceContainerLowest = SurfaceContainerLowestNatureDark,
    surfaceContainerLow = SurfaceContainerLowNatureDark,
    surfaceContainer = SurfaceContainerNatureDark,
    surfaceContainerHigh = SurfaceContainerHighNatureDark,
    surfaceContainerHighest = SurfaceContainerHighestNatureDark,
    outline = OutlineNatureDark,
    outlineVariant = OutlineVariantNatureDark
)


// =========================================
// MAIN COMPOSABLE
// =========================================
@Composable
fun HealthTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeType: AppThemeType = AppThemeType.DEFAULT,
    fontSize: AppFontSize = AppFontSize.MEDIUM, // Nhận thêm tham số cỡ chữ từ ngoài truyền vào
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeType) {
        AppThemeType.DEFAULT -> if (darkTheme) DefaultDarkColorScheme else DefaultLightColorScheme
        AppThemeType.SERENE_OCEAN -> if (darkTheme) SereneOceanDarkColorScheme else SereneOceanLightColorScheme
        AppThemeType.VIBRANT_ENERGY -> if (darkTheme) VibrantEnergyDarkColorScheme else VibrantEnergyLightColorScheme
        AppThemeType.NATURE_VITALITY -> if (darkTheme) NatureVitalityDarkColorScheme else NatureVitalityLightColorScheme
    }
    val fontScale = when (fontSize) {
        AppFontSize.SMALL -> 0.85f
        AppFontSize.MEDIUM -> 1.0f
        AppFontSize.LARGE -> 1.15f
    }

    // Ghi đè Typography với fontScale
    val scaledTypography = Typography.let { typography ->
        androidx.compose.material3.Typography(
            displayLarge = typography.displayLarge.copy(fontSize = typography.displayLarge.fontSize * fontScale),
            headlineLarge = typography.headlineLarge.copy(fontSize = typography.headlineLarge.fontSize * fontScale),
            titleLarge = typography.titleLarge.copy(fontSize = typography.titleLarge.fontSize * fontScale),
            bodyLarge = typography.bodyLarge.copy(fontSize = typography.bodyLarge.fontSize * fontScale),
            labelLarge = typography.labelLarge.copy(fontSize = typography.labelLarge.fontSize * fontScale),
            labelMedium = typography.labelMedium.copy(fontSize = typography.labelMedium.fontSize * fontScale)
        )
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalDimens provides Dimensions()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = scaledTypography,
            shapes = Shapes,
            content = content
        )
    }
}
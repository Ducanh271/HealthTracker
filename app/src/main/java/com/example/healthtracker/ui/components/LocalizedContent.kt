package com.example.healthtracker.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

val LocalAppContext = staticCompositionLocalOf<Context?> { null }

@Composable
fun LocalizedContent(content: @Composable () -> Unit) {
    val appContext = LocalAppContext.current

    if (appContext == null) {
        content()
        return
    }

    CompositionLocalProvider(
        LocalContext provides appContext,
        LocalConfiguration provides appContext.resources.configuration,
        content = content
    )
}

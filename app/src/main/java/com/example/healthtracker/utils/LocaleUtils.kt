package com.example.healthtracker.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleUtils {

    fun localizedContext(context: Context, languageTag: String): Context {
        val locale = Locale.forLanguageTag(languageTag.lowercase())
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
}

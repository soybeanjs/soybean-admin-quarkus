/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.infrastructure.localization

import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import java.text.MessageFormat
import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle

private val fallbackLocale = Locale.CHINA

@ApplicationScoped
class LocalizationService(
    private val routingContext: RoutingContext,
) {
    fun getMessage(key: String): String =
        try {
            getResourceBundle().getString(key)
        } catch (e: MissingResourceException) {
            key
        }

    fun getMessage(
        key: String,
        vararg params: Any,
    ): String = MessageFormat.format(getMessage(key), *params)

    private fun getResourceBundle(): ResourceBundle = ResourceBundle.getBundle("messages", getPreferredLocale())

    private fun getPreferredLocale(): Locale =
        parseAcceptLanguage(routingContext.request().headers()["Accept-Language"]).firstOrNull() ?: fallbackLocale

    private fun parseAcceptLanguage(acceptLanguage: String?): List<Locale> {
        if (acceptLanguage.isNullOrEmpty()) return emptyList()

        val languages = mutableMapOf<Locale, Double>()

        acceptLanguage.split(",").forEach { part ->
            val sections = part.split(";q=")
            val locale = Locale.forLanguageTag(sections[0])
            val quality = if (sections.size > 1) sections[1].toDoubleOrNull() ?: 1.0 else 1.0
            languages[locale] = quality
        }

        return languages.entries.sortedByDescending { it.value }.map { it.key }
    }
}

package com.peteralexbizjak.europaopen.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

internal inline fun <reified T> T.serialize(): String = json.encodeToString(this)

internal inline fun <reified T> String.deserialize(): T = json.decodeFromString(this)
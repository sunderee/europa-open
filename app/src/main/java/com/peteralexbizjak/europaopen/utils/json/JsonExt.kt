package com.peteralexbizjak.europaopen.utils.json

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal val json = Json {
    ignoreUnknownKeys = true
}

internal inline fun <reified T> T.serialize(): String = json.encodeToString(this)

internal inline fun <reified T> String.deserialize(): T = json.decodeFromString(this)

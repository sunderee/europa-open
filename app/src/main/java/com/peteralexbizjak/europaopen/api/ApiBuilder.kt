package com.peteralexbizjak.europaopen.api

import com.peteralexbizjak.europaopen.utils.JsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://reopen.europa.eu/"

internal fun buildRetrofit(baseURL: String = BASE_URL): Retrofit = Retrofit.Builder()
    .baseUrl(baseURL)
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
                redactHeader("Authorization")
                redactHeader("Cookie")
            })
            .build()
    )
    .addConverterFactory(JsonConverterFactory.create())
    .build()

internal fun <T> buildService(retrofit: Retrofit, service: Class<T>) = retrofit.create(service)
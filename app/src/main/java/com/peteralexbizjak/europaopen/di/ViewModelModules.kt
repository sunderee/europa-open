package com.peteralexbizjak.europaopen.di

import android.content.Context
import androidx.room.Room
import com.peteralexbizjak.europaopen.api.buildRetrofit
import com.peteralexbizjak.europaopen.api.buildService
import com.peteralexbizjak.europaopen.api.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.CountryRepository
import com.peteralexbizjak.europaopen.api.services.CountryService
import com.peteralexbizjak.europaopen.db.AppDatabase
import com.peteralexbizjak.europaopen.db.daos.CountryDao
import com.peteralexbizjak.europaopen.db.repositories.ICountryDBRepository
import com.peteralexbizjak.europaopen.db.repositories.implementations.CountryDBRepository
import com.peteralexbizjak.europaopen.ui.landing.LandingViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE_NAME = "cache"

private fun getDatabaseInstance(context: Context): AppDatabase = Room
    .databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
    .fallbackToDestructiveMigration()
    .build()

private fun provideCountryDAO(database: AppDatabase): CountryDao = database.countryDao()

@ExperimentalSerializationApi
private val retrofitInstance = buildRetrofit()

@ExperimentalSerializationApi
internal val landingViewModel = module {
    single { buildService(retrofitInstance, CountryService::class.java) }
    single<ICountryRepository> { CountryRepository(service = get()) }

    single { getDatabaseInstance(androidContext()) }
    single { provideCountryDAO(database = get()) }
    single<ICountryDBRepository> { CountryDBRepository(countryDAO = get()) }

    viewModel { LandingViewModel(apiRepository = get(), databaseRepository = get()) }
}
package com.peteralexbizjak.europaopen.di

import android.content.Context
import androidx.room.Room
import com.peteralexbizjak.europaopen.api.buildRetrofit
import com.peteralexbizjak.europaopen.api.buildService
import com.peteralexbizjak.europaopen.api.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.CountryRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.MeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.RegionRepository
import com.peteralexbizjak.europaopen.api.services.CountryService
import com.peteralexbizjak.europaopen.api.services.MeasuresService
import com.peteralexbizjak.europaopen.api.services.RegionService
import com.peteralexbizjak.europaopen.db.AppDatabase
import com.peteralexbizjak.europaopen.db.daos.CountryDao
import com.peteralexbizjak.europaopen.db.daos.DomainDao
import com.peteralexbizjak.europaopen.db.daos.RegionDao
import com.peteralexbizjak.europaopen.db.repositories.ICountryDBRepository
import com.peteralexbizjak.europaopen.db.repositories.IDomainDBRepository
import com.peteralexbizjak.europaopen.db.repositories.IRegionDBRepository
import com.peteralexbizjak.europaopen.db.repositories.implementations.CountryDBRepository
import com.peteralexbizjak.europaopen.db.repositories.implementations.DomainDBRepository
import com.peteralexbizjak.europaopen.db.repositories.implementations.RegionDBRepository
import com.peteralexbizjak.europaopen.ui.landing.LandingViewModel
import com.peteralexbizjak.europaopen.ui.statistics.StatisticsViewModel
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
private fun provideRegionDAO(database: AppDatabase): RegionDao = database.regionDao()
private fun provideDomainDAO(database: AppDatabase): DomainDao = database.domainDao()

@ExperimentalSerializationApi
private val retrofitInstance = buildRetrofit()

@ExperimentalSerializationApi
internal val landingModule = module {
    single { buildService(retrofitInstance, CountryService::class.java) }
    single<ICountryRepository> { CountryRepository(service = get()) }

    single { getDatabaseInstance(androidContext()) }
    single { provideCountryDAO(database = get()) }
    single<ICountryDBRepository> { CountryDBRepository(countryDAO = get()) }

    viewModel { LandingViewModel(apiRepository = get(), databaseRepository = get()) }
}

@ExperimentalSerializationApi
internal val statisticsModule = module(override = true) {
    single { buildService(retrofitInstance, MeasuresService::class.java) }
    single<IMeasureRepository> { MeasureRepository(service = get()) }

    single { buildService(retrofitInstance, RegionService::class.java) }
    single<IRegionRepository> { RegionRepository(service = get()) }

    single { getDatabaseInstance(androidContext()) }
    single { provideRegionDAO(database = get()) }
    single { provideDomainDAO(database = get()) }
    single<IRegionDBRepository> { RegionDBRepository(regionDao = get()) }
    single<IDomainDBRepository> { DomainDBRepository(domainDao = get()) }

    viewModel {
        StatisticsViewModel(
            measureRepository = get(),
            regionRepository = get(),
            regionDBRepository = get(),
            domainDBRepository = get()
        )
    }
}
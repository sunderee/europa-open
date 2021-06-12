package com.peteralexbizjak.europaopen

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.peteralexbizjak.europaopen.api.buildRetrofit
import com.peteralexbizjak.europaopen.api.buildService
import com.peteralexbizjak.europaopen.api.models.country.CountryModel
import com.peteralexbizjak.europaopen.api.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.CountryRepository
import com.peteralexbizjak.europaopen.api.services.CountryService
import com.peteralexbizjak.europaopen.db.AppDatabase
import com.peteralexbizjak.europaopen.db.daos.CountryDao
import com.peteralexbizjak.europaopen.db.entities.CountryEntity
import com.peteralexbizjak.europaopen.db.repositories.ICountryDBRepository
import com.peteralexbizjak.europaopen.db.repositories.implementations.CountryDBRepository
import com.peteralexbizjak.europaopen.utils.constants.Direction
import com.peteralexbizjak.europaopen.utils.exceptions.ApiException
import com.peteralexbizjak.europaopen.utils.exceptions.DatabaseException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
internal class AppDatabaseInstrumentedTest : KoinTest {
    private val countryModule = module(override = true) {
        single { buildService(buildRetrofit(), CountryService::class.java) }
        single<ICountryRepository> { CountryRepository(service = get()) }
    }

    private val databaseModule = module(override = true) {
        fun getDatabaseInstance(): AppDatabase =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
            ).build()

        fun provideCountryDAO(database: AppDatabase): CountryDao = database.countryDao()

        single { getDatabaseInstance() }
        single { provideCountryDAO(database = get()) }
        single<ICountryDBRepository> { CountryDBRepository(countryDAO = get()) }
    }

    private val repository by inject<ICountryRepository>()
    private val database by inject<ICountryDBRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(listOf(countryModule, databaseModule))
    }

    /**
     * The idea when it comes to fetching countries is to implement caching via local SQLite database
     * which will get populated on application's first run. Once done, this will greatly decrease
     * networking cost because you don't need to query the *extremely fast* API.
     */
    @Test
    fun testCachingAlgorithm() {
        // First run... You query the API because cache has nothing
        val countries = runBlocking { performMerge() }
        println("=== FIRST RUN ===")
        println("FROM ${countries.first.joinToString(", ") { it.shortName }}")
        println("TO: ${countries.second.joinToString(", ") { it.shortName }}")

        // Second run... You should retrieve data from cache
        val cachedCountries = runBlocking { performMerge() }
        println("=== SECOND RUN ===")
        println("FROM ${cachedCountries.first.joinToString(", ") { it.shortName }}")
        println("TO: ${cachedCountries.second.joinToString(", ") { it.shortName }}")

        // God bless if these things pass
        assertTrue(countries.first.isNotEmpty())
        assertTrue(countries.second.isNotEmpty())
        assertTrue(cachedCountries.first.isNotEmpty())
        assertTrue(cachedCountries.first.isNotEmpty())

        assertEquals(true, countries.first.size == cachedCountries.first.size)
        assertEquals(true, countries.second.size == cachedCountries.second.size)
        assertEquals(
            true,
            countries.first.sortedBy { it.shortName } == cachedCountries.first.sortedBy { it.shortName }
        )
        assertEquals(
            true,
            countries.second.sortedBy { it.shortName } == cachedCountries.second.sortedBy { it.shortName }
        )
    }

    // Similar to CountryViewModel.init logic
    @Throws(DatabaseException::class, ApiException::class)
    private suspend fun performMerge(): Pair<List<CountryModel>, List<CountryModel>> {
        val fromCountries by lazy { mutableListOf<CountryModel>() }
        val toCountries by lazy { mutableListOf<CountryModel>() }

        val isEmpty = database.isTableEmpty()
        if (isEmpty) {
            val from = repository.fetchCountries(Direction.FROM).toMutableList()
            val to = repository.fetchCountries(Direction.TO).toMutableList()
            fromCountries.addAll(from)
            toCountries.addAll(to)

            val both = from.intersect(to).map {
                CountryEntity(
                    countryShortName = it.shortName,
                    countryLongName = it.longName,
                    appearsInDirection = "both"
                )
            }
            both.forEach { entity ->
                from.removeIf { it.shortName == entity.countryShortName }
                to.removeIf { it.shortName == entity.countryShortName }
            }
            database.storeCountries(
                *both.toTypedArray(),
                *from.map {
                    CountryEntity(
                        countryShortName = it.shortName,
                        countryLongName = it.longName,
                        appearsInDirection = "from"
                    )
                }.toTypedArray(),
                *to.map {
                    CountryEntity(
                        countryShortName = it.shortName,
                        countryLongName = it.longName,
                        appearsInDirection = "to"
                    )
                }.toTypedArray()
            )
        } else {
            fromCountries.addAll(
                database
                    .retrieveCountries("from")
                    .map { CountryModel(it.countryShortName, it.countryLongName) })
            toCountries.addAll(
                database
                    .retrieveCountries("to")
                    .map { CountryModel(it.countryShortName, it.countryLongName) }
            )
        }

        return Pair(fromCountries, toCountries)
    }
}
package com.peteralexbizjak.europaopen.api

import com.peteralexbizjak.europaopen.api.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.CountryRepository
import com.peteralexbizjak.europaopen.api.services.CountryService
import com.peteralexbizjak.europaopen.utils.constants.Direction
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

internal class CountryUnitTest : KoinTest {
    private val countryModule = module(override = true) {
        single { buildService(buildRetrofit(), CountryService::class.java) }
        single<ICountryRepository> { CountryRepository(service = get()) }
    }
    private val repository by inject<ICountryRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(countryModule)
    }

    @Test
    fun fetchCountriesFrom() {
        val countries = runBlocking { repository.fetchCountries(Direction.FROM) }
        println("FROM countries: ${countries.joinToString(", ") { it.shortName }}")
        assertTrue(countries.isNotEmpty())
    }

    @Test
    fun fetchCountriesTo() {
        val countries = runBlocking { repository.fetchCountries(Direction.TO) }
        println("TO countries: ${countries.joinToString(", ") { it.shortName }}")
        assertTrue(countries.isNotEmpty())
    }
}
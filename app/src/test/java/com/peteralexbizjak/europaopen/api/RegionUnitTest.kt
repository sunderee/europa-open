package com.peteralexbizjak.europaopen.api

import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.RegionRepository
import com.peteralexbizjak.europaopen.api.services.RegionService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

internal class RegionUnitTest : KoinTest {
    private val regionModule = module(override = true) {
        single { buildService(buildRetrofit(), RegionService::class.java) }
        single<IRegionRepository> { RegionRepository(service = get()) }
    }
    private val repository by inject<IRegionRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(regionModule)
    }

    @Test
    fun testRequestingFrenchRegions() {
        val frenchRegions = runBlocking { repository.requestRegionsPerCountry("FRA") }
        println("French regions: ${frenchRegions.joinToString("\n") { "${it.region}: ${it.color}" }}")
        assertTrue(frenchRegions.isNotEmpty())
    }
}
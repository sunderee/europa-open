package com.peteralexbizjak.europaopen.api

import com.peteralexbizjak.europaopen.api.repositories.ITravelRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.TravelRepository
import com.peteralexbizjak.europaopen.api.services.TravelService
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class TravelUnitTest : KoinTest {
    private val travelModule = module(override = true) {
        single { buildService(buildRetrofit(), TravelService::class.java) }
        single<ITravelRepository> { TravelRepository(service = get()) }
    }
    private val repository by inject<ITravelRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(travelModule)
    }

    @Test
    fun fetchTravelInfo() {
        val start = "SVN"
        val destination = "ESP"
        val travelInfo = runBlocking { repository.fetchTravelInfo(start, destination) }
        println("Travel info from SVN -> ESP")
        travelInfo.forEach { println(it.countryCode + "\n" + it.data.first().restrictions) }
    }

    @Test
    fun fetchTravelInfoWithTransient() {
        val start = "SVN"
        val destination = "ESP"
        val transient = "HRV"
        val travelInfo = runBlocking { repository.fetchTravelInfo(start, destination, transient) }
        println("Travel info from SVN -> ESP via HRV")
        travelInfo.forEach { println(it.countryCode + "\n" + it.data.first().restrictions) }
    }
}
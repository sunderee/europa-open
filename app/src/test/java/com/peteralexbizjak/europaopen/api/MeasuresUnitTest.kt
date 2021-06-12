package com.peteralexbizjak.europaopen.api

import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.MeasureRepository
import com.peteralexbizjak.europaopen.api.services.MeasuresService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertTrue

internal class MeasuresUnitTest : KoinTest {
    private val measuresModule = module(override = true) {
        single { buildService(buildRetrofit(), MeasuresService::class.java) }
        single<IMeasureRepository> { MeasureRepository(service = get()) }
    }
    private val repository by inject<IMeasureRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(measuresModule)
    }

    @Test
    fun testFetchingDomains() {
        val domains = runBlocking { repository.fetchDomains() }
        println("Domains: ${domains.joinToString(", ") { it.name }}")
        assertEquals(4, domains.size)
    }

    @Test
    fun testFetchingDomainIndicators() {
        val indicators = runBlocking { repository.fetchDomainIndicators(listOf(7, 6, 5, 8)) }
        indicators.forEach {
            println("Domain: ${it.id}")
            println(it.indicators.joinToString("\n") { id -> id.name })
        }
        assertTrue(indicators.isNotEmpty())
    }

    @Test
    fun testFetchingRules() {
        val rules = runBlocking { repository.fetchRules("SVN", listOf(7010, 2002)) }
        rules.forEach {
            println("Rules:\n${it.data.joinToString("\n") { it.indicator }}")
        }
        assertEquals(2, rules[0].data.size)
    }
}
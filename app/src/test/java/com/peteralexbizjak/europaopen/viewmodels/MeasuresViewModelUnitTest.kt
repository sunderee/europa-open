package com.peteralexbizjak.europaopen.viewmodels

import com.peteralexbizjak.europaopen.api.buildRetrofit
import com.peteralexbizjak.europaopen.api.buildService
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.implementations.MeasureRepository
import com.peteralexbizjak.europaopen.api.services.MeasuresService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertTrue

class MeasuresViewModelUnitTest : KoinTest {
    private data class Rule(
        val title: String,
        val comment: String
    )

    private data class Indicator(
        val domainID: Int,
        val title: String,
        val comment: String? = null,
        val rules: List<Rule> = emptyList()
    )

    private data class Domain(
        val title: String,
        val indicators: List<Indicator>
    )

    private val measuresModule = module(override = true) {
        single { buildService(buildRetrofit(), MeasuresService::class.java) }
        single<IMeasureRepository> { MeasureRepository(service = get()) }
    }
    private val repository by inject<IMeasureRepository>()

    @get:org.junit.Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(measuresModule)
    }

    @Test
    fun testDomainCollection() {
        val countryCode = "SVN"
        val data = runBlocking {
            val domains = repository.fetchDomains()
            println("Domains: ${domains.joinToString(", ") { it.id.toString() }}")

            val indicators = repository
                .fetchDomainIndicators(domains.map { it.id })
                .flatMap { it.indicators }
                .map { indicator ->
                    val indicatorID = indicator.id
                    val rules = repository.fetchRules(
                        countryCode,
                        listOf(indicatorID, *indicator.rules.toTypedArray())
                    ).flatMap { it.data }
                    Indicator(
                        rules
                            .find { it.id == indicatorID }
                            ?.domainID ?: -1,
                        indicator.name,
                        rules
                            .find { it.id == indicatorID }
                            ?.restrictions ?: "No data",
                        rules
                            .filter { indicator.rules.contains(it.id) }
                            .map { Rule(it.indicator, it.restrictions) }
                    )
                }
            domains.map { domain ->
                Domain(
                    domain.name,
                    indicators.filter { it.domainID == domain.id }
                )
            }
        }
        println("Domains: $data")
        assertTrue(data.isNotEmpty())
    }
}
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
            println("Indicators: ${indicators.joinToString(", ") { it.id.toString() }}")
            val allRules = repository.fetchRules(
                countryCode,
                indicators.flatMap { it.rules }
            ).flatMap { it.data }
            println("Rules: ${allRules.joinToString(", ") { it.id.toString() }}")

            val domainData = domains.map {
                Domain(
                    it.name,
                    indicators
                        .filter { indicator -> indicator.domainID == it.id }
                        .map { indicator ->
                            Indicator(
                                indicator.name,
                                indicator.contents,
                                allRules
                                    .filter { rule ->
                                        indicator.rules.contains(rule.id)
                                    }
                                    .map { rule ->
                                        Rule(
                                            rule.indicator,
                                            rule.restrictions
                                        )
                                    }
                            )
                        }
                )
            }
            domainData
        }
        println("Domains: $data")
        assertTrue(data.isNotEmpty())
    }
}
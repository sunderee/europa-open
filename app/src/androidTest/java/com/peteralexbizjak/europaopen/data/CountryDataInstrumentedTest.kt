package com.peteralexbizjak.europaopen.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.peteralexbizjak.europaopen.api.RetrofitBuilder
import com.peteralexbizjak.europaopen.api.services.CountryService
import com.peteralexbizjak.europaopen.data.enums.CountryDirectionEnum
import com.peteralexbizjak.europaopen.data.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.data.repositories.implementations.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertTrue

@ExperimentalSerializationApi
@RunWith(AndroidJUnit4::class)
internal class CountryDataInstrumentedTest : KoinTest {
    private val countryRepository by inject<ICountryRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
        modules(
            module {
                single {
                    RetrofitBuilder.buildService(
                        retrofit = RetrofitBuilder.buildRetrofit(),
                        service = CountryService::class.java
                    )
                }
                single {
                    DataStoreProvider.dataStoreProvider(
                        androidContext(),
                        DataStoreProvider.countryMessageSerializer
                    )
                }
                single<ICountryRepository> {
                    CountryRepository(
                        countryService = get(),
                        countryDataStore = get()
                    )
                }
            }
        )
    }

    @Test
    fun testCountryViewModelState() {
        runBlocking(Dispatchers.IO) {
            val countries = countryRepository.retrieveCountries(CountryDirectionEnum.DIRECTION_TO)
            println("We have found ${countries.size} countries")
            assertTrue {
                countries.isNotEmpty()
            }

            val randomCountry = countries.random()
            println("Random country: $randomCountry")
            assertTrue {
                randomCountry != null
            }
        }
    }
}
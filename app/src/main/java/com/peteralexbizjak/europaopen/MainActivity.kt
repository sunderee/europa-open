package com.peteralexbizjak.europaopen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.peteralexbizjak.europaopen.databinding.ActivityMainBinding
import com.peteralexbizjak.europaopen.di.countriesViewModelModule
import com.peteralexbizjak.europaopen.di.measuresViewModelModule
import com.peteralexbizjak.europaopen.di.travelViewModelModule
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @ExperimentalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainActivity)
            modules(countriesViewModelModule, measuresViewModelModule, travelViewModelModule)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
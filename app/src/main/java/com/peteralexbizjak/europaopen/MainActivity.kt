package com.peteralexbizjak.europaopen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.peteralexbizjak.europaopen.databinding.ActivityMainBinding
import com.peteralexbizjak.europaopen.di.countriesViewModelModule
import com.peteralexbizjak.europaopen.di.measuresViewModelModule
import com.peteralexbizjak.europaopen.di.travelViewModelModule
import com.peteralexbizjak.europaopen.ui.viewmodels.MeasuresViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val measuresViewModel by viewModel<MeasuresViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainActivity)
            modules(countriesViewModelModule, measuresViewModelModule, travelViewModelModule)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainToolbar)
        val navController = findNavController(R.id.activityMainNavigationHost)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.activityMainNavigationHost)
            .navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
}
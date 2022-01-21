package com.github.amrmsaraya.expirytracker.presentation.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.ActivityMainBinding
import com.github.amrmsaraya.expirytracker.worker.ExpiryWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setTheme(R.style.Theme_App)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        schedulePeriodicWorkRequest()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun schedulePeriodicWorkRequest() {
        val workRequest =
            PeriodicWorkRequestBuilder<ExpiryWorker>(1L, TimeUnit.HOURS).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "checkExpiredProducts",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }
}
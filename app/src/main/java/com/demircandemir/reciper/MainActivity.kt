 package com.demircandemir.reciper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.demircandemir.reciper.navigation.RecipeNavigationGraph
import com.demircandemir.reciper.ui.theme.RecipeFinderTheme
import com.demircandemir.reciper.workmanager.RecipeUpdateWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {

     @Inject
     lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeFinderTheme {
                RecipeNavigationGraph(modifier = Modifier.fillMaxSize())
            }
        }


        // WorkManager'ı başlat
        WorkManager.initialize(this, androidx.work.Configuration.Builder().setWorkerFactory(workerFactory).build())

        val workRequest = PeriodicWorkRequestBuilder<RecipeUpdateWorker>(4, TimeUnit.DAYS)
            .setInitialDelay(10, TimeUnit.SECONDS)  // İşin ilk olarak 1 saat sonra başlamasını sağlar
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "RecipeUpdateWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}
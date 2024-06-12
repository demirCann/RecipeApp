package com.demircandemir.reciper.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.demircandemir.reciper.R
import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.ApiResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull


@HiltWorker
class RecipeUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: MealRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        Log.d("RecipeUpdateWorker", "Work started")
        try {
            // Tarif verilerini güncelle
            val response = repository.getMealsForTypes(
                type = "breakfast",
                number = 10
            ).firstOrNull()

            when (response) {
                is ApiResult.Success -> {
                    Log.d("RecipeUpdateWorker", "API call success")
                    if (response.data?.results?.isNotEmpty() == true) {
                        showNotification(response.data.results)
                    }
                    Result.success()
                }

                is ApiResult.Error -> {
                    Log.e("RecipeUpdateWorker", "API call error: ${response.message}")
                    Result.failure()
                }

                is ApiResult.Loading -> {
                    Log.d("RecipeUpdateWorker", "API call loading")
                    Result.retry()
                }

                null -> Result.failure()
            }
        } catch (e: Exception) {
            Log.e("RecipeUpdateWorker", "Exception: ${e.localizedMessage}")
            Result.failure()
        }
    }

    private fun showNotification(newRecipes: List<com.demircandemir.reciper.data.source.network.response.Result>) {
        Log.d("RecipeUpdateWorker", "Showing notification")
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel oluştur
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "recipe_updates",
                "Recipe Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for recipe updates"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Bildirim oluştur ve göster
        val notification = NotificationCompat.Builder(applicationContext, "recipe_updates")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("New Recipes Available")
            .setContentText("Check out the latest recipes!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }
}
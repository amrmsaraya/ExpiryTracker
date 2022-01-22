package com.github.amrmsaraya.expirytracker.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.amrmsaraya.expirytracker.BuildConfig
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.domain.usecase.GetNotNotifiedExpiredProductsUseCase
import com.github.amrmsaraya.expirytracker.domain.usecase.InsertProductsUseCase
import com.github.amrmsaraya.expirytracker.presentation.main_activity.MainActivity
import com.github.amrmsaraya.expirytracker.utils.createNotification
import com.github.amrmsaraya.expirytracker.utils.createNotificationChannel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlin.random.Random

private const val CHANNEL_ID = BuildConfig.APPLICATION_ID

@HiltWorker
class ExpiryWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    private val getNotNotifiedExpiredProductsUseCase: GetNotNotifiedExpiredProductsUseCase,
    private val insertProductsUseCase: InsertProductsUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent = Intent(context, MainActivity::class.java).let {
            PendingIntent.getActivity(
                context,
                0,
                it,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_MUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )
        }

        context.createNotificationChannel(
            id = CHANNEL_ID,
            name = context.getString(R.string.expired_products),
            importance = NotificationManager.IMPORTANCE_HIGH
        )

        val expiredItems =
            getNotNotifiedExpiredProductsUseCase.invoke().firstOrNull() ?: emptyList()

        expiredItems.filter { !it.isNotified }.forEach { product ->
            val notification = context.createNotification(
                channelId = CHANNEL_ID,
                title = context.getString(R.string.expired_product),
                content = "${product.name} ${context.getString(R.string.has_been_expired)}",
                icon = R.drawable.ic_round_hourglass_full_24,
                intent = pendingIntent
            )

            notificationManager.notify(Random.nextInt(1000), notification)
            insertProductsUseCase.invoke(product.copy(isNotified = true))
        }

        return Result.success()
    }
}
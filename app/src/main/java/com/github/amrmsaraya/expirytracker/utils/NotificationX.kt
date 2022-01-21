package com.github.amrmsaraya.expirytracker.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat


fun Context.createNotificationChannel(
    id: String,
    name: String,
    importance: Int
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(id, name, importance)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun Context.createNotification(
    channelId: String,
    title: String,
    content: String,
    @DrawableRes icon: Int,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
): Notification {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationCompat.Builder(this, channelId).apply {
            setContentTitle(title)
            setContentText(content)
            setSmallIcon(icon)
            setPriority(priority)
        }.build()
    } else {
        NotificationCompat.Builder(this).apply {
            setContentTitle(title)
            setContentText(content)
            setSmallIcon(icon)
            setPriority(priority)
        }.build()
    }
}
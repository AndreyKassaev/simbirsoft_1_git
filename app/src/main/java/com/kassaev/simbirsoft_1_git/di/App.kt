package com.kassaev.simbirsoft_1_git.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelDonation = NotificationChannel(
                "donation_channel", "Donation Notifications",
                NotificationManager.IMPORTANCE_LOW
            )
            val channelNews = NotificationChannel(
                "news_channel",
                "Event Asset Reader",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager.createNotificationChannel(channelNews)
            notificationManager.createNotificationChannel(channelDonation)
        }
    }
}
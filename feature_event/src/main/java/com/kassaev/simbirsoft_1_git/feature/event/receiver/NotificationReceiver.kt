package com.kassaev.simbirsoft_1_git.feature.event.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.kassaev.simbirsoft_1_git.feature.event.worker.DonationReminderWorker
import java.util.concurrent.TimeUnit

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val eventId = intent?.getStringExtra("event_id")
        val eventName = intent?.getStringExtra("event_name")

        context?.let {
            val notificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(1)
        }

        if (eventId != null && eventName != null && context != null) {
            startDonationReminderWorker(
                eventId = eventId,
                eventName = eventName,
                context = context
            )
        }
    }

    private fun startDonationReminderWorker(
        eventId: String,
        eventName: String,
        context: Context
    ) {
        val delayMillis = TimeUnit.SECONDS.toMillis(5)

        val inputData = Data.Builder()
            .putString("event_id", eventId)
            .putString("event_name", eventName)
            .build()

        WorkManager
            .getInstance(context = context)
            .enqueue(
                OneTimeWorkRequestBuilder<DonationReminderWorker>()
                    .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                    .setInputData(inputData)
                    .build()
            )
    }
}
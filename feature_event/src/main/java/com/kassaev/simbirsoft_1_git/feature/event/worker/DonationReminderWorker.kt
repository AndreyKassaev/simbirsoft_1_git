package com.kassaev.simbirsoft_1_git.feature.event.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kassaev.simbirsoft_1_git.core.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import androidx.core.net.toUri

@HiltWorker
class DonationReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val eventId = inputData.getString("event_id") ?: return Result.failure()
        val eventName = inputData.getString("event_name") ?: return Result.failure()

        createNotification(
            eventId = eventId,
            eventName = eventName,
        )

        return Result.success()
    }

    private fun createNotification(
        eventName: String,
        eventId: String,
    ) {

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val deepLinkUri = "com.kassaev.iwanttohelp://event_detail?eventId=$eventId".toUri()
        val intent =  Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            `package` = applicationContext.packageName
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "donation_channel")
            .setContentTitle(eventName)
            .setContentText("Напоминаем, что мы будем очень признательны, если вы сможете пожертвовать еще больше")
            .setSmallIcon(R.drawable.ruble)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOngoing(false)
            .build()

        notificationManager.notify(1, notification)
    }
}

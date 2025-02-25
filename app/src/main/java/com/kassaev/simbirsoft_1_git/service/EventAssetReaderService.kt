package com.kassaev.simbirsoft_1_git.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class EventAssetReaderService: Service() {
    private val binder = LocalBinder()
    private val eventRepository: EventRepository by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        readFile()
        return START_STICKY
    }

    fun readFile() = eventRepository.getEventListFlow()

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return false
    }

    private fun createNotification(): Notification {
        val channelId = "EventAssetReaderChannel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Event Asset Reader", NotificationManager.IMPORTANCE_HIGH)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(this.getString(R.string.loading))
            .setContentText(this.getString(R.string.loading))
            .setSmallIcon(R.drawable.downloading)
            .build()
    }

    inner class LocalBinder : Binder() {
        fun getService(): EventAssetReaderService = this@EventAssetReaderService
    }

    override fun onBind(intent: Intent?): IBinder? = binder
}
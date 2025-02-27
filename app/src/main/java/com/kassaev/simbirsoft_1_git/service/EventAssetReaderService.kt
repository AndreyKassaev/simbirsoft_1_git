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
import com.kassaev.simbirsoft_1_git.di.DaggerEntryPoint
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import dagger.hilt.android.EntryPointAccessors

class EventAssetReaderService: Service() {

    private val binder = LocalBinder()

    lateinit var eventRepository: EventRepository

    override fun onCreate() {
        super.onCreate()
        val entryPoint = EntryPointAccessors.fromApplication(applicationContext, DaggerEntryPoint::class.java)
        eventRepository = entryPoint.eventRepository()
    }

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
            val channel = NotificationChannel(channelId, "Event Asset Reader", NotificationManager.IMPORTANCE_LOW)
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
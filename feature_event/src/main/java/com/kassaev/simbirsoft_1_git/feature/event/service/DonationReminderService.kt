package com.kassaev.simbirsoft_1_git.feature.event.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class DonationReminderService: Service() {
    override fun onBind(intent: Intent?): IBinder? = null
}
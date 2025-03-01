package com.kassaev.simbirsoft_1_git.feature.event.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.IBinder
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.kassaev.simbirsoft_1_git.core.datastore.dataStore
import com.kassaev.simbirsoft_1_git.feature.event.worker.DonationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class BatteryChargingNotificationService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var batteryReceiver: BroadcastReceiver

    override fun onCreate() {
        super.onCreate()
        batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    checkDonation()
                }
            }
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun checkDonation(){
        scope.launch {
            val donationString = applicationContext.dataStore.data.first()[stringPreferencesKey("donation")]
            clearDonation()
            if (!donationString.isNullOrEmpty()) {
                startWorker(
                    donation = Json.decodeFromString<Donation>(donationString)
                )
            }
        }
    }

    private fun clearDonation() {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey("donation")] = ""
            }
        }
    }

    private fun startWorker(donation: Donation) {
        val inputData = Data.Builder()
            .putString("event_id", donation.eventId)
            .putString("event_name", donation.eventName)
            .putString("donation_amount", donation.donationAmount)
            .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(
                OneTimeWorkRequestBuilder<DonationWorker>()
                    .setInputData(inputData)
                    .build()
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

@Serializable
data class Donation(
    val eventId: String,
    val eventName: String,
    val donationAmount: String,
)
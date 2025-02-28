package com.kassaev.simbirsoft_1_git.feature.event.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DonationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val eventId = inputData.getString("event_id") ?: return Result.failure()
        val eventName = inputData.getString("event_name") ?: return Result.failure()
        val donationAmount = inputData.getString("donation_amount") ?: return Result.failure()
        println("event_id: $eventId, event_name: $eventName, donation_amount: $donationAmount")
        return Result.success()
    }
}

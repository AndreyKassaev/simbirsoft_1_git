package com.kassaev.simbirsoft_1_git.feature.event.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.core.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.feature.event.worker.DonationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.work.Data
import com.kassaev.simbirsoft_1_git.core.util.Event
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
) : ViewModel() {

    @Inject
    lateinit var workManager: WorkManager

    private val stateFlowMutable =
        MutableStateFlow<EventDetailScreenState>(EventDetailScreenState.Loading())
    private val stateFlow: StateFlow<EventDetailScreenState> = stateFlowMutable

    private val isDialogOpenFlowMutable = MutableStateFlow(false)
    private val isDialogOpenFlow: StateFlow<Boolean> = isDialogOpenFlowMutable

    private val donationAmountFlowMutable = MutableStateFlow("")
    private val donationAmount: StateFlow<String> = donationAmountFlowMutable

    private val eventFlowMutable = MutableStateFlow<Event?>(null)

    init {
        viewModelScope.launch {
            val eventId = savedStateHandle.toRoute<Router.EventDetail>().eventId
            stateFlowMutable.update {
                if (eventId != null) {
                    val event = getEventById(id = eventId)
                    eventFlowMutable.update {
                        event
                    }
                    EventDetailScreenState.Success(
                        data = event
                    )
                } else {
                    EventDetailScreenState.Error()
                }
            }

        }
    }

    fun getStateFlow() = stateFlow

    fun getIsDialogOpenFlow() = isDialogOpenFlow

    fun getDonationAmount() = donationAmount

    fun setDonationAmount(amount: String) {
        donationAmountFlowMutable.update {
            amount
        }
    }

    fun setIsDialogOpen(bool: Boolean) {
        isDialogOpenFlowMutable.update {
            bool
        }
    }

    fun donate() {
        viewModelScope.launch {
            eventFlowMutable.collectLatest { event ->
                donationAmount.collectLatest { donationAmount ->
                    event?.let {
//                        TODO() 0. create foreground service with dynamic broadcast receiver for battery charge, that checks sharedPrefs if battery is charging And launch foreground service if data exists. 1. save data to sharedPrefs.
//                        val inputData = Data.Builder()
//                            .putString("event_id", event.id)
//                            .putString("event_name", event.title)
//                            .putString("donation_amount", donationAmount)
//                            .build()
//
//                        val workRequest = OneTimeWorkRequest.Builder(DonationWorker::class.java)
//                            .setInputData(inputData)
//                            .build()
//
//                        workManager.enqueue(workRequest)
                    }
                }
            }
        }
    }

    private suspend fun getEventById(id: String) = eventRepository.getEventById(id = id)
}
package com.kassaev.simbirsoft_1_git.feature.event.screen

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.work.WorkManager
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.core.repository.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.kassaev.simbirsoft_1_git.core.datastore.dataStore
import com.kassaev.simbirsoft_1_git.core.util.Event
import com.kassaev.simbirsoft_1_git.feature.event.service.Donation
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository,
    @ApplicationContext private val context: Context,
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
                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("donation")] = Json.encodeToString<Donation>(Donation(
                                eventId = event.id,
                                eventName = event.title,
                                donationAmount = donationAmount
                            ))
                        }
                    }
                }
            }
        }
    }

    private suspend fun getEventById(id: String) = eventRepository.getEventById(id = id)
}
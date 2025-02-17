package com.kassaev.simbirsoft_1_git.screen.event_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kassaev.simbirsoft_1_git.navigation.Router
import com.kassaev.simbirsoft_1_git.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val eventMutable = MutableStateFlow<Event?>(null)
    private val event: StateFlow<Event?> = eventMutable

    init {
        val eventId = savedStateHandle.toRoute<Router.EventDetail>().
        eventId?.let { id ->
            viewModelScope.launch {
                eventMutable.update {
                    getEventById(eventId = id)
                }
            }
        }
    }

    fun getEventFlow() = event

    private fun getEventById(eventId: String) = Event.default

}
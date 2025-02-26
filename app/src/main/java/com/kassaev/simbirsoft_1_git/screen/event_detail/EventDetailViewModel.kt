package com.kassaev.simbirsoft_1_git.screen.event_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kassaev.simbirsoft_1_git.navigation.Router
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
): ViewModel() {

    private val stateFlowMutable = MutableStateFlow<EventDetailScreenState>(EventDetailScreenState.Loading())
    private val stateFlow: StateFlow<EventDetailScreenState> = stateFlowMutable

    init {
        viewModelScope.launch {
            val eventId = savedStateHandle.toRoute<Router.EventDetail>().eventId
            stateFlowMutable.update {
                if (eventId != null) {
                    EventDetailScreenState.Success(
                        data = getEventById(id = eventId)
                    )
                } else {
                    EventDetailScreenState.Error()
                }
            }

        }
    }

    fun getStateFlow() = stateFlow

    private suspend fun getEventById(id: String) = eventRepository.getEventById(id = id)

}
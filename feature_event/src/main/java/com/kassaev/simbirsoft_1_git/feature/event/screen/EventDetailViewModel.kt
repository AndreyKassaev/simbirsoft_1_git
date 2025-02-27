package com.kassaev.simbirsoft_1_git.feature.event.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.core.repository.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
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
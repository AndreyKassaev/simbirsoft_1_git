package com.kassaev.simbirsoft_1_git.screen.news

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.EventRepository
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.FilterSwitchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val eventRepository: EventRepository
): ViewModel() {

    private val newsListMutable = MutableStateFlow<List<Event>>(emptyList())
    private val newsList: StateFlow<List<Event>> = newsListMutable

    private val filterSwitchStateMutable = MutableStateFlow<FilterSwitchState>(FilterSwitchState.default)
    private val filterSwitchState: StateFlow<FilterSwitchState> = filterSwitchStateMutable

    init {
        viewModelScope.launch {
            newsListMutable.update {
                eventRepository.getEventList()
            }
        }
    }

    fun getNewsListFlow() = newsList

    fun getFilterSwitchStateFlow() = filterSwitchState

    fun setFilterSwitchMoneyState(state: Boolean) {
        viewModelScope.launch {
            filterSwitchStateMutable.update { currentState ->
                currentState.copy(
                    money = state
                )
            }
        }
    }

    fun setFilterSwitchStuffState(state: Boolean) {
        viewModelScope.launch {
            filterSwitchStateMutable.update { currentState ->
                currentState.copy(
                    stuff = state
                )
            }
        }
    }
}
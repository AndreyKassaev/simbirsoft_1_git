package com.kassaev.simbirsoft_1_git.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.FilterSwitchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    private val newsListMutable = MutableStateFlow<List<Event>>(List(33) { Event.default })
    private val newsList: StateFlow<List<Event>> = newsListMutable

    private val filterSwitchStateMutable = MutableStateFlow<FilterSwitchState>(FilterSwitchState.default)
    private val filterSwitchState: StateFlow<FilterSwitchState> = filterSwitchStateMutable

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
package com.kassaev.simbirsoft_1_git.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.util.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val stateMutableFlow = MutableStateFlow<SearchScreenState>(SearchScreenState.Init())
    private val stateFlow: StateFlow<SearchScreenState> = stateMutableFlow

    private val searchValueMutable = MutableStateFlow("")
    private val searchValue: StateFlow<String> = searchValueMutable

    init {
        updateStateWithSearchQuery()
    }

    fun getStateFlow() = stateFlow

    fun getSearchValueFlow() = searchValue

    fun setSearchValue(value: String) {
        viewModelScope.launch {
            searchValueMutable.update {
                value
            }
        }
    }

    private fun updateStateWithSearchQuery() {
        viewModelScope.launch {
            searchValue.debounce(500L).collectLatest { searchText ->
                val trimmedSearchText = searchText.trim().replace(Regex("\\s+"), " ")
                if (trimmedSearchText.isEmpty()) {
                    stateMutableFlow.update {
                        SearchScreenState.Init()
                    }
                } else {
                    val currKeywordList = if (trimmedSearchText.contains(" ")) {
                        trimmedSearchText.split(" ")
                    } else {
                        listOf(trimmedSearchText)
                    }
                    eventRepository.getEventListFlow().collectLatest { eventList ->
                        val filteredEventList = mutableSetOf<Event>()

                        currKeywordList.forEach { word ->
                            eventList.find { event ->
                                event.title.contains(word, ignoreCase = true)
                            }?.let { foundEvent ->
                                filteredEventList.add(foundEvent)
                            }
                        }

                        stateMutableFlow.update {
                            if (filteredEventList.isNotEmpty()) {
                                SearchScreenState.Success(
                                    data = SearchScreenData.default.copy(
                                        eventList = filteredEventList.toList(),
                                        keywordList = currKeywordList
                                    )
                                )
                            } else {
                                SearchScreenState.Empty()
                            }
                        }
                    }
                }
            }
        }
    }
}

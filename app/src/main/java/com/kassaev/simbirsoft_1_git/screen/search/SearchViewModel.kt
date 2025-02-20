package com.kassaev.simbirsoft_1_git.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.Npo
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
): ViewModel() {

    private val stateMutable = MutableStateFlow<SearchScreenState>(SearchScreenState.Init())
    private val state: StateFlow<SearchScreenState> = stateMutable

    private val searchValueMutable = MutableStateFlow<String>("")
    private val searchValue: StateFlow<String> = searchValueMutable

    init {
        viewModelScope.launch {
            searchValue.debounce(500L).collectLatest { searchText ->
                if (searchText.isEmpty()) {
                    stateMutable.update {
                        SearchScreenState.Init()
                    }
                } else {

                    val currKeywordList = if (searchText.isNotEmpty()) searchText.split(" ") else listOf(searchText)
                    val allEventList = eventRepository.getEventList()
                    val filteredEventList = mutableSetOf<Event>()
                    currKeywordList.forEach { word ->
                        allEventList.find { event ->
                            event.title.contains(word)
                        }?.let { foundEvent ->
                            filteredEventList.add(foundEvent)
                        }
                    }
                    stateMutable.update {
                        if (filteredEventList.isNotEmpty()) {
                            SearchScreenState.Success(data = SearchScreenData.default.copy(eventList = filteredEventList.toList(), keywordList = currKeywordList))
                        } else {
                            SearchScreenState.Empty()
                        }
                    }
                }
            }
        }
    }

    fun getStateFlow() = state

    fun getSearchValueFlow() = searchValue

    fun setSearchValue(value: String) {
        viewModelScope.launch {
            searchValueMutable.update {
                value
            }
        }
    }
}
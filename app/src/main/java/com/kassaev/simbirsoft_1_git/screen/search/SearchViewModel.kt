package com.kassaev.simbirsoft_1_git.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.getSearchQuery
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
        updateStateWithSearchQuery2()
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

    private fun updateStateWithSearchQuery2() {
        viewModelScope.launch {
            searchValue.debounce(500L).collectLatest { searchText ->
                if (searchText.isEmpty()) {
                    stateMutableFlow.update {
                        SearchScreenState.Init()
                    }
                } else {
                    eventRepository.findByAnyWord(query = getSearchQuery(searchText)).collectLatest { foundEventList ->
                        stateMutableFlow.update {
                            if (foundEventList.isNotEmpty()) {
                                SearchScreenState.Success(
                                    data = SearchScreenData.default.copy(
                                        eventList = foundEventList,
                                        keywordList = searchText.trim().replace(Regex("\\s+"), " ")
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

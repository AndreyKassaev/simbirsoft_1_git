package com.kassaev.simbirsoft_1_git.screen.search

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.Npo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val searchValueMutable = MutableStateFlow<String>("")
    private val searchValue: StateFlow<String> = searchValueMutable

    private val eventListMutable = MutableStateFlow<List<Event>>(List(42) { Event.default})
    private val eventList: StateFlow<List<Event>> = eventListMutable

    private val npoListMutable = MutableStateFlow<List<Npo>>(List(33) { Npo.default })
    private val npoList: StateFlow<List<Npo>> = npoListMutable

    private val keywordListMutable = MutableStateFlow<List<String>>(List(3) { "keyword" })
    private val keywordList: StateFlow<List<String>> = keywordListMutable

    fun getSearchValueFlow() = searchValue

    fun setSearchValue(value: String) {
        viewModelScope.launch {
            searchValueMutable.update {
                value
            }
        }
    }

    fun getEventListFlow() = eventList

    fun getNpoListFlow() = npoList

    fun getKeywordList() = keywordList
}
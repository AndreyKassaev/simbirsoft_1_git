package com.kassaev.simbirsoft_1_git.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.util.Event
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val stateSubject =
        BehaviorSubject.createDefault<SearchScreenState>(SearchScreenState.Init())

    private val searchValueMutable = MutableStateFlow("")
    private val searchValue: StateFlow<String> = searchValueMutable

    init {
        viewModelScope.launch(Dispatchers.IO) {
            searchValue.debounce(500L).collectLatest { searchText ->
                updateState(searchText)
            }
        }
    }

    fun getStateObservable(): Observable<SearchScreenState> = stateSubject.hide()

    fun getSearchValue() = searchValue

    fun setSearchValue(value: String) {
        viewModelScope.launch {
            searchValueMutable.update {
                value
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun updateState(searchText: String) {
        val trimmedSearchText = searchText.trim().replace(Regex("\\s+"), " ")
        if (trimmedSearchText.isEmpty()) {
            stateSubject.onNext(SearchScreenState.Init())
        } else {
            val currKeywordList = if (trimmedSearchText.contains(" ")) {
                trimmedSearchText.split(" ")
            } else {
                listOf(trimmedSearchText)
            }

            val allEventList = eventRepository.getEventList()
            val filteredEventList = mutableSetOf<Event>()

            currKeywordList.forEach { word ->
                allEventList.find { event ->
                    event.title.contains(word, ignoreCase = true)
                }?.let { foundEvent ->
                    filteredEventList.add(foundEvent)
                }
            }
            stateSubject.onNext(
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
            )
        }
    }
}

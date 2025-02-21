package com.kassaev.simbirsoft_1_git.screen.search

import androidx.lifecycle.ViewModel
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.util.Event
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val stateSubject = BehaviorSubject.createDefault<SearchScreenState>(SearchScreenState.Init())
    private val searchValueSubject = BehaviorSubject.createDefault("")

    init {
        searchValueSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { searchText ->
                val trimmedSearchText = searchText.trim().replace(Regex("\\s+"), " ")
                if (trimmedSearchText.isEmpty()) {
                    Observable.just(SearchScreenState.Init())
                } else {
                    Observable.fromCallable {
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
            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe(
                { state -> stateSubject.onNext(state) },
                { error -> error.printStackTrace() }
            )
            .let { disposables.add(it) }
    }

    fun getStateObservable(): Observable<SearchScreenState> = stateSubject.hide()
    fun getSearchValueObservable(): Observable<String> = searchValueSubject.hide()

    fun setSearchValue(value: String) {
        searchValueSubject.onNext(value)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

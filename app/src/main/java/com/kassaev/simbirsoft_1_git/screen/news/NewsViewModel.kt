package com.kassaev.simbirsoft_1_git.screen.news

import androidx.lifecycle.ViewModel
import com.kassaev.simbirsoft_1_git.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel: ViewModel() {

    private val newsListMutable = MutableStateFlow<List<Event>>(List(33) { Event.default })
    private val newsList: StateFlow<List<Event>> = newsListMutable

    fun getNewsListFlow() = newsList

}
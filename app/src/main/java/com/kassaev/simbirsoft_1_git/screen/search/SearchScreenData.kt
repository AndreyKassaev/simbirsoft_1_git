package com.kassaev.simbirsoft_1_git.screen.search

import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.Npo

data class SearchScreenData(
    val eventList: List<Event>,
    val npoList: List<Npo>,
    val keywordList: String,
) {
    companion object {
        val default = SearchScreenData(
            eventList = emptyList(),
            npoList = emptyList(),
            keywordList = ""
        )
    }
}
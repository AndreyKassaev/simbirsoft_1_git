package com.kassaev.simbirsoft_1_git.feature.search.screen

import com.kassaev.simbirsoft_1_git.core.util.Event
import com.kassaev.simbirsoft_1_git.core.util.Npo

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
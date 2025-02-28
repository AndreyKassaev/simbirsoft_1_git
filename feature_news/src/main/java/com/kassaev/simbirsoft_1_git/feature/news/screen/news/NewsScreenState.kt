package com.kassaev.simbirsoft_1_git.feature.news.screen.news

import com.kassaev.simbirsoft_1_git.core.util.Event

sealed class NewsScreenState {
    class Success(
        val data: List<Event>
    ): NewsScreenState()
    class Loading: NewsScreenState()
    class Failure(
        val msg: String
    ): NewsScreenState()
}
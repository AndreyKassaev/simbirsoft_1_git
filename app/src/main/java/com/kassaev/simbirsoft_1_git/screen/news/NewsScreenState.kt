package com.kassaev.simbirsoft_1_git.screen.news

import com.kassaev.simbirsoft_1_git.util.Event

sealed class NewsScreenState {
    class Success(
        val data: List<Event>
    ): NewsScreenState()
    class Loading: NewsScreenState()
    class Failure(
        val msg: String
    ): NewsScreenState()
}
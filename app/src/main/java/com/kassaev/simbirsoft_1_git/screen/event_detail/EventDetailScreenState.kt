package com.kassaev.simbirsoft_1_git.screen.event_detail

import com.kassaev.simbirsoft_1_git.util.Event

sealed class EventDetailScreenState {
    class Success(val data: Event): EventDetailScreenState()
    class Error(): EventDetailScreenState()
    class Loading(): EventDetailScreenState()
}
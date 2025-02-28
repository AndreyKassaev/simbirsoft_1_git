package com.kassaev.simbirsoft_1_git.feature.event.screen

import com.kassaev.simbirsoft_1_git.core.util.Event

sealed class EventDetailScreenState {
    class Success(val data: Event): EventDetailScreenState()
    class Error(): EventDetailScreenState()
    class Loading(): EventDetailScreenState()
}
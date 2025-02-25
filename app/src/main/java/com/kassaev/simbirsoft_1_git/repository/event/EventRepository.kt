package com.kassaev.simbirsoft_1_git.repository.event

import com.kassaev.simbirsoft_1_git.util.Event
import kotlinx.coroutines.flow.StateFlow


interface EventRepository {
    fun getEventById(id: String): Event?
    fun getEventListFlow(): StateFlow<List<Event>>
}
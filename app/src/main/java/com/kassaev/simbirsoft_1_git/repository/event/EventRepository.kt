package com.kassaev.simbirsoft_1_git.repository.event

import com.kassaev.simbirsoft_1_git.util.Event

interface EventRepository {
    fun getEventList(): List<Event>
    fun getEventById(id: String): Event?
}
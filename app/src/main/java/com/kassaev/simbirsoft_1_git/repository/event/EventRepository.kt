package com.kassaev.simbirsoft_1_git.repository.event

import com.kassaev.simbirsoft_1_git.util.Category
import com.kassaev.simbirsoft_1_git.api.model.Event as EventResponse
import com.kassaev.simbirsoft_1_git.util.Event
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


interface EventRepository {
    fun getEventList(): List<Event>
    fun getEventById(id: String): Event?
    fun getEvents(categoryId: String): Single<List<EventResponse>>
    fun getEventListObservable(): Observable<List<Event>>
}
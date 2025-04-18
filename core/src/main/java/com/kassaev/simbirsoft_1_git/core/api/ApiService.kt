package com.kassaev.simbirsoft_1_git.core.api

import com.kassaev.simbirsoft_1_git.core.api.model.Category
import com.kassaev.simbirsoft_1_git.core.api.model.Event
import com.kassaev.simbirsoft_1_git.core.api.model.EventRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("categories")
    suspend fun getCategories(): Map<String, Category>

    @POST("events")
    suspend fun getEvents(@Body request: EventRequest): List<Event>
}
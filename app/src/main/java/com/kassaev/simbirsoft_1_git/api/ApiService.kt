package com.kassaev.simbirsoft_1_git.api

import com.kassaev.simbirsoft_1_git.api.model.Category
import com.kassaev.simbirsoft_1_git.api.model.Event
import com.kassaev.simbirsoft_1_git.api.model.EventRequest
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("categories")
    fun getCategories(): Single<Map<String, Category>>

    @POST("events")
    fun getEvents(@Body request: EventRequest): Single<List<Event>>
}
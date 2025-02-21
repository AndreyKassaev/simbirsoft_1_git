package com.kassaev.simbirsoft_1_git.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Router{
    @Serializable
    data object News: Router()
    @Serializable
    data object Search: Router()
    @Serializable
    data object Help: Router()
    @Serializable
    data object History: Router()
    @Serializable
    data object Profile: Router()
    @Serializable
    data class EventDetail(
        val eventId: String? = null
    ): Router()
    @Serializable
    data object Authorization: Router()
}


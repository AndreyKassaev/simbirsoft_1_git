package com.kassaev.simbirsoft_1_git.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Router{
    @Serializable
    data object MainScreen: Router()
    @Serializable
    data object ProfileScreen: Router()
}


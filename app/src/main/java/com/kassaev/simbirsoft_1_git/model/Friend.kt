package com.kassaev.simbirsoft_1_git.model

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val imageUrl: String
)
package com.kassaev.simbirsoft_1_git.core.util

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val imageUrl: String
) {
    companion object {
        val default = Friend(
            id = "UUID-0002",
            firstName = "Имя",
            middleName = "Отчество",
            lastName = "Фамилия",
            imageUrl = "https://kassaev.com/media/android_11.png"
        )
    }
}
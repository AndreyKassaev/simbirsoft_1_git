package com.kassaev.simbirsoft_1_git.model

data class Profile(
    val id: String,
    val imageUrl: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val birthDate: String,
    val occupation: String,
    val isPushEnabled: Boolean,
    val friendList: List<Friend>
)
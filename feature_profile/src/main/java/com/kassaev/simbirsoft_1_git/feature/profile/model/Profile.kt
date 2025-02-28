package com.kassaev.simbirsoft_1_git.feature.profile.model

import com.kassaev.simbirsoft_1_git.core.util.Friend

data class Profile(
    val id: String,
    val imageUrl: String?,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val birthDate: String,
    val occupation: String,
    val isPushEnabled: Boolean,
    val password: String,
    val friendList: List<Friend>
) {
    companion object {
        val default = Profile(
            id = "UUID-0001",
            imageUrl = "https://kassaev.com/media/android_11.png",
            firstName = "Имя",
            middleName = "",
            lastName = "Фамилия",
            birthDate = "01 января 1970",
            occupation = "Computer science",
            isPushEnabled = true,
            password = "SeCreTTT!",
            friendList = List(10) {
                Friend.default
            }
        )
    }
}
package com.kassaev.simbirsoft_1_git.util

import com.kassaev.simbirsoft_1_git.model.Friend
import com.kassaev.simbirsoft_1_git.model.Profile

fun getMockProfile() =
    Profile(
        id = "UUID-0001",
        imageUrl = "https://kassaev.com/media/android_11.png",
        firstName = "Имя",
        middleName = "",
        lastName = "Фамилия",
        birthDate = "01 января 1970",
        occupation = "Computer science",
        isPushEnabled = true,
        friendList = List(10) {
            getMockFriend()
        }
    )
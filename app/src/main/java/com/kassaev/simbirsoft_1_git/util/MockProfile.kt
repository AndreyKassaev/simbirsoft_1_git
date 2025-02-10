package com.kassaev.simbirsoft_1_git.util

import com.kassaev.simbirsoft_1_git.model.Friend
import com.kassaev.simbirsoft_1_git.model.Profile

fun getMockProfile() =
    Profile(
        id = "UUID-0001",
        imageUrl = "https://kassaev.com/media/android_11.png",
        firstName = "Денис",
        middleName = "",
        lastName = "Константинов",
        birthDate = "10 февраля 1980",
        occupation = "Хириргия, травматология",
        isPushEnabled = true,
        friendList = List(10) {
            getMockFriend()
        }
    )
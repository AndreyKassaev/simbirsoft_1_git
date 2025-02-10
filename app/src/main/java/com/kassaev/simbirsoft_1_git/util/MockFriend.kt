package com.kassaev.simbirsoft_1_git.util

import com.kassaev.simbirsoft_1_git.model.Friend

fun getMockFriend() =
    Friend(
        id = "UUID-0002",
        firstName = "Имя",
        middleName = "Отчество",
        lastName = "Фамилия",
        imageUrl = ""
    )
package com.kassaev.simbirsoft_1_git.util

import kotlinx.serialization.Serializable

@Serializable
data class HelpCategory(
    val title: String,
    val imageUrl: String,
)

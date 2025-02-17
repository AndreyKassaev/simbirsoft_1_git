package com.kassaev.simbirsoft_1_git.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

data class Event(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val timestamp: Long
) {
    companion object {
        val default = Event(
            id = "UUID-0001",
            imageUrl = "https://kassaev.com/media/android_14.png",
            title = LoremIpsum(4).values.joinToString(" "),
            description = LoremIpsum(13).values.joinToString(" "),
            timestamp = System.currentTimeMillis()
        )
    }
}

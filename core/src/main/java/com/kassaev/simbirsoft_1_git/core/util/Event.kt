package com.kassaev.simbirsoft_1_git.core.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val imageUrlList: List<String>,
    val title: String,
    val description: String,
    val timestamp: Long,
    val organization: String,
    val people: List<Friend> = emptyList(),
    val category: String,
    val isWatched: Boolean
) {
    companion object {
        val default = Event(
            id = "UUID-0001",
            imageUrlList = listOf("https://kassaev.com/media/android_14.png",),
            title = LoremIpsum(4).values.joinToString(" "),
            description = LoremIpsum(13).values.joinToString(" "),
            timestamp = System.currentTimeMillis(),
            organization = LoremIpsum(4).values.joinToString(" "),
            people = List(42) { Friend.default },
            category = "",
            isWatched = false
        )
    }
}
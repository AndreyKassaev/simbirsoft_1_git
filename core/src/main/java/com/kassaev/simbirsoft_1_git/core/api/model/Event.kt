package com.kassaev.simbirsoft_1_git.core.api.model

data class EventRequest(
    val id: String = ""
)

data class Event(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val description: String,
    val status: Long,
    val photos: List<String>,
    val category: String,
    val createdAt: Long,
    val phone: String,
    val address: String,
    val organization: String
) {
    companion object {
        val default = Event(
            id = "",
            name = "",
            startDate = 0L,
            endDate = 0L,
            description = "",
            status = 0L,
            photos = emptyList(),
            category = "",
            createdAt = 0L,
            phone = "",
            address = "",
            organization = ""
        )
    }
}
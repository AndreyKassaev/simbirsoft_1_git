package com.kassaev.simbirsoft_1_git.core.util

import kotlinx.serialization.Serializable

@Serializable
data class EventAsset(
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
)

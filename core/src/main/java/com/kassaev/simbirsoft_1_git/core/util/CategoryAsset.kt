package com.kassaev.simbirsoft_1_git.core.util

import kotlinx.serialization.Serializable

@Serializable
data class CategoryAsset(
    val id: String,
    val nameEn: String,
    val name: String,
    val image: String
)

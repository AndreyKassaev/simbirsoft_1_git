package com.kassaev.simbirsoft_1_git.core.util

data class Category(
    val id: String,
    val nameEn: String,
    val name: String,
    val image: String
) {
    companion object {
        val default = Category(
            id = "1",
            nameEn = "default",
            name = "default",
            image = "imageUrl"
        )
    }
}

package com.kassaev.simbirsoft_1_git.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

data class Npo(
    val title: String
) {
    companion object {
        val default = Npo(
            title = LoremIpsum(33).values.joinToString(" ")
        )
    }
}

package com.kassaev.simbirsoft_1_git.screen.help

import androidx.lifecycle.ViewModel
import com.kassaev.simbirsoft_1_git.util.HelpCategory
import com.kassaev.simbirsoft_1_git.R

class HelpViewModel: ViewModel() {

    private val categoryList = listOf(
        HelpCategory(
            title = R.string.young,
            image = R.drawable.young,
        ),
        HelpCategory(
            title = R.string.adult,
            image = R.drawable.adult,
        ),
        HelpCategory(
            title = R.string.senior,
            image = R.drawable.senior,
        ),
        HelpCategory(
            title = R.string.animal,
            image = R.drawable.animal,
        ),
        HelpCategory(
            title = R.string.event,
            image = R.drawable.event,
        ),
    )

    fun getCategoryList() = categoryList
}
package com.kassaev.simbirsoft_1_git.feature.help.screen

import com.kassaev.simbirsoft_1_git.core.util.Category

sealed class HelpScreenState {
    class Success(
        val data: Map<String, Category>
    ): HelpScreenState()
    class Loading: HelpScreenState()
    class Failure(
        val msg: String
    ): HelpScreenState()
}
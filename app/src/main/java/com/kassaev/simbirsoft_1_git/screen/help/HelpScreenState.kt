package com.kassaev.simbirsoft_1_git.screen.help

import com.kassaev.simbirsoft_1_git.util.Category

sealed class HelpScreenState {
    class Success(
        val data: Map<String, Category>
    ): HelpScreenState()
    class Loading: HelpScreenState()
    class Failure(
        val msg: String
    ): HelpScreenState()
}
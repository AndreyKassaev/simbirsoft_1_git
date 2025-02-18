package com.kassaev.simbirsoft_1_git.screen.help

import com.kassaev.simbirsoft_1_git.util.HelpCategory

sealed class HelpScreenState {
    class Success(
        val data: List<HelpCategory>
    ): HelpScreenState()
    class Loading: HelpScreenState()
    class Failure(
        val msg: String
    ): HelpScreenState()
}
package com.kassaev.simbirsoft_1_git.screen.search

sealed class SearchScreenState {
    class Success(val data: SearchScreenData): SearchScreenState()
    class Loading: SearchScreenState()
    class Init: SearchScreenState()
    class Empty: SearchScreenState()
    class Failure(val msg: String): SearchScreenState()
}
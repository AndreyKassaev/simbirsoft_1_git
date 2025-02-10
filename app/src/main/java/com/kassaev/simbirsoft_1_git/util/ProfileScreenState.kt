package com.kassaev.simbirsoft_1_git.util

sealed class ProfileScreenState {
    object Edit: ProfileScreenState()
    object Preview: ProfileScreenState()
}
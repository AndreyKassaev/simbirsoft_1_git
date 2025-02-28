package com.kassaev.simbirsoft_1_git.feature.profile.screen

sealed class ProfileScreenState {
    object Edit: ProfileScreenState()
    object Preview: ProfileScreenState()
}
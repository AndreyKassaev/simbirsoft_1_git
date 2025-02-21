package com.kassaev.simbirsoft_1_git.util

data class BottomBarItem<T : Any>(
    @androidx.annotation.DrawableRes val icon: Int,
    @androidx.annotation.StringRes val title: Int,
    val route: T,
    val badgeCount: Int? = null
)
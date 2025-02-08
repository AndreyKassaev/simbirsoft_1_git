package com.kassaev.simbirsoft_1_git.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomBarItem<T : Any>(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: T,
)

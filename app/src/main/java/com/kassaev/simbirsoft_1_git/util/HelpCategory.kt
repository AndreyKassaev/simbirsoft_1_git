package com.kassaev.simbirsoft_1_git.util

import androidx.annotation.StringRes
import androidx.annotation.DrawableRes

data class HelpCategory(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
)

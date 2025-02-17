package com.kassaev.simbirsoft_1_git.util

data class FilterSwitchState(
    val money: Boolean,
    val stuff: Boolean,
) {
    companion object {
        val default = FilterSwitchState(
            money = true,
            stuff = true
        )
    }
}

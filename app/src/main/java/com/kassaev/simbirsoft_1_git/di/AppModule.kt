package com.kassaev.simbirsoft_1_git.di

import com.kassaev.simbirsoft_1_git.screen.help.HelpViewModel
import com.kassaev.simbirsoft_1_git.screen.profile.ProfileViewModel
import com.kassaev.simbirsoft_1_git.screen.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module{
    viewModelOf(::ProfileViewModel)
    viewModelOf(::HelpViewModel)
    viewModelOf(::SearchViewModel)
}
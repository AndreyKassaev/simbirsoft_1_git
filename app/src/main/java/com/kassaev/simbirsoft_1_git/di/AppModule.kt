package com.kassaev.simbirsoft_1_git.di

import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepositoryImpl
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.repository.event.EventRepositoryImpl
import com.kassaev.simbirsoft_1_git.screen.event_detail.EventDetailViewModel
import com.kassaev.simbirsoft_1_git.screen.help.HelpViewModel
import com.kassaev.simbirsoft_1_git.screen.news.NewsViewModel
import com.kassaev.simbirsoft_1_git.screen.profile.ProfileViewModel
import com.kassaev.simbirsoft_1_git.screen.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.bind

val appModule = module{
    viewModelOf(::ProfileViewModel)
    viewModelOf(::HelpViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::NewsViewModel)
    viewModelOf(::EventDetailViewModel)
    single { androidContext().assets }
    singleOf(::EventRepositoryImpl) bind EventRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
}
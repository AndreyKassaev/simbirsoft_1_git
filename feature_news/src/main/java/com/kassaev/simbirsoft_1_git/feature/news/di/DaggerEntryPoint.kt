package com.kassaev.simbirsoft_1_git.feature.news.di

import com.kassaev.simbirsoft_1_git.core.repository.event.EventRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DaggerEntryPoint {
    fun eventRepository(): EventRepository
}
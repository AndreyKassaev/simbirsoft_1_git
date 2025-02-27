package com.kassaev.simbirsoft_1_git.di

import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DaggerEntryPoint {
    fun eventRepository(): EventRepository
}
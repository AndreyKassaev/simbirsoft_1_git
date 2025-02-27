package com.kassaev.simbirsoft_1_git.feature.news.di

import com.kassaev.simbirsoft_1_git.feature.news.service.EventAssetReaderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class NewsModule {
    @Provides
    fun provideEventAssetReaderService(): EventAssetReaderService = EventAssetReaderService()
}

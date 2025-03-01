package com.kassaev.simbirsoft_1_git.core.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.kassaev.simbirsoft_1_git.core.api.ApiService
import com.kassaev.simbirsoft_1_git.core.database.Database
import com.kassaev.simbirsoft_1_git.core.database.dao.CategoryDao
import com.kassaev.simbirsoft_1_git.core.database.dao.EventDao
import com.kassaev.simbirsoft_1_git.core.repository.category.CategoryRepositoryImpl
import com.kassaev.simbirsoft_1_git.core.repository.category.CategoryRepository
import com.kassaev.simbirsoft_1_git.core.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.core.repository.event.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideAssetManager(application: Application): AssetManager = application.assets

    @Singleton
    @Provides
    fun provideApiService(): ApiService {

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://backend.kassaev.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(application: Application): Database {
        return Room.databaseBuilder(application, Database::class.java, "app.db").build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: Database): CategoryDao = database.categoryDao()

    @Singleton
    @Provides
    fun provideEventDao(database: Database): EventDao = database.eventDao()

    @Singleton
    @Provides
    fun provideCategoryRepository(assetManager: AssetManager, apiService: ApiService, categoryDao: CategoryDao): CategoryRepository =
        CategoryRepositoryImpl(
            assetManager = assetManager,
            apiService = apiService,
            categoryDao = categoryDao,
        )

    @Singleton
    @Provides
    fun provideEventRepository(assetManager: AssetManager, apiService: ApiService, eventDao: EventDao): EventRepository =
        EventRepositoryImpl(
            assetManager = assetManager,
            apiService = apiService,
            eventDao = eventDao,
        )
}
package com.kassaev.simbirsoft_1_git.di

import com.kassaev.simbirsoft_1_git.api.ApiService
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepositoryImpl
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.repository.event.EventRepositoryImpl
import com.kassaev.simbirsoft_1_git.screen.authorization.AuthorizationViewModel
import com.kassaev.simbirsoft_1_git.screen.event_detail.EventDetailViewModel
import com.kassaev.simbirsoft_1_git.screen.help.HelpViewModel
import com.kassaev.simbirsoft_1_git.screen.news.NewsViewModel
import com.kassaev.simbirsoft_1_git.screen.profile.ProfileViewModel
import com.kassaev.simbirsoft_1_git.screen.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module{
    viewModelOf(::ProfileViewModel)
    singleOf(::HelpViewModel)
    viewModelOf(::SearchViewModel)
    singleOf(::NewsViewModel)
    viewModelOf(::EventDetailViewModel)
    single { androidContext().assets }
    singleOf(::EventRepositoryImpl) bind EventRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    viewModelOf(::AuthorizationViewModel)
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://backend.kassaev.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
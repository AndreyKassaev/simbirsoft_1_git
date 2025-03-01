package com.kassaev.simbirsoft_1_git.core.repository.event

import androidx.sqlite.db.SupportSQLiteQuery
import com.kassaev.simbirsoft_1_git.core.util.Event
import kotlinx.coroutines.flow.Flow


interface EventRepository {
    fun findByAnyWord(searchText: String): Flow<List<Event>>
    suspend fun getEventById(id: String): Event
    fun getEventListFlow(): Flow<List<Event>>
}
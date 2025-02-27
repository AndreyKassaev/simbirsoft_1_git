package com.kassaev.simbirsoft_1_git.repository.event

import androidx.sqlite.db.SupportSQLiteQuery
import com.kassaev.simbirsoft_1_git.util.Event
import kotlinx.coroutines.flow.Flow


interface EventRepository {
    fun findByAnyWord(query: SupportSQLiteQuery): Flow<List<Event>>
    suspend fun getEventById(id: String): Event
    fun getEventListFlow(): Flow<List<Event>>
}
package com.kassaev.simbirsoft_1_git.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kassaev.simbirsoft_1_git.database.entity.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAll(): Flow<List<Event>>

    @RawQuery(observedEntities = [Event::class])
    fun findByAnyWord(query: SupportSQLiteQuery): Flow<List<Event>>

    @Query("SELECT * FROM event WHERE category LIKE :category")
    fun findByCategory(category: String): Flow<List<Event>>

    @Query("SELECT * FROM event WHERE id = :id")
    suspend fun findById(id: String): Event

    @Query("UPDATE event SET isWatched = 1 WHERE id = :id")
    suspend fun markAsWatched(id: String)

    @Transaction
    suspend fun findAndMarkAsWatched(id: String): Event =
        findById(id).also { markAsWatched(id) }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(eventList: List<Event>)
}
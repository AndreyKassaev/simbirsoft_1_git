package com.kassaev.simbirsoft_1_git.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kassaev.simbirsoft_1_git.database.entity.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAll(): Flow<List<Event>>

    @Query("SELECT * FROM event WHERE id = :id")
    fun findById(id: String): Event

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(eventList: List<Event>)
}
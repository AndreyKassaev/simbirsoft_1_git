package com.kassaev.simbirsoft_1_git.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kassaev.simbirsoft_1_git.database.dao.CategoryDao
import com.kassaev.simbirsoft_1_git.database.dao.EventDao
import com.kassaev.simbirsoft_1_git.database.entity.Category
import com.kassaev.simbirsoft_1_git.database.entity.Event

@Database(
    entities = [Event::class, Category::class], version = 1
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun categoryDao(): CategoryDao
}
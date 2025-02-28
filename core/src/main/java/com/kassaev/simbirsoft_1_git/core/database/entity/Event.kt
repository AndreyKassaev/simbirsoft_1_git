package com.kassaev.simbirsoft_1_git.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(name = "start_date")
    val startDate: Long,
    @ColumnInfo(name = "end_date")
    val endDate: Long,
    val description: String,
    val status: Long,
    val photos: List<String>,
    val category: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    val phone: String,
    val address: String,
    val organization: String,
    val isWatched: Boolean,
)

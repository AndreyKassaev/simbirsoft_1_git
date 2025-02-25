package com.kassaev.simbirsoft_1_git.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name_en")
    val nameEn: String,
    val name: String,
    val image: String
)

package com.kassaev.simbirsoft_1_git.util

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

fun getSearchQuery(input: String): SupportSQLiteQuery {
    val words = input.trim().split("\\s+".toRegex())
    val whereClauses = words.joinToString(" OR ") { "name LIKE '%' || ? || '%'" }
    val query = "SELECT * FROM event WHERE $whereClauses COLLATE NOCASE"
    return SimpleSQLiteQuery(query, words.toTypedArray())
}
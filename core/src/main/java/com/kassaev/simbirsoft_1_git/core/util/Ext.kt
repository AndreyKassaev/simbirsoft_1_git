package com.kassaev.simbirsoft_1_git.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toTimestamp() = SimpleDateFormat("dd MMMM yyyy", Locale("ru")).parse(this).time
fun Long.toDateString() = SimpleDateFormat("dd MMMM yyyy", Locale("ru")).format(Date(this))
fun String.capitalizeFirstLetter() = this.lowercase().replaceFirstChar { it.uppercaseChar() }

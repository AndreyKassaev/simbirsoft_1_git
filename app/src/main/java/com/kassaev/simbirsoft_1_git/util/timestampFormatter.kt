package com.kassaev.simbirsoft_1_git.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timestamFormatter(timestamp: Long) =
    SimpleDateFormat(
        "dd MMMM yyyy",
        Locale("ru")
    ).format(Date(timestamp))
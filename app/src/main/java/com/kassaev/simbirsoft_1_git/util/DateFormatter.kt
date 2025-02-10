package com.kassaev.simbirsoft_1_git.util

import java.text.SimpleDateFormat
import java.util.Date

fun timestampTodateFormatter(timestamp: Long) = SimpleDateFormat("dd MMMM yyyy").format(Date(timestamp))

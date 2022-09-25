package com.example.presentation.util

import java.text.SimpleDateFormat
import java.util.*


fun getToday() : String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val curDate = Date()
    return format.format(curDate)
}

fun getTomorrow() : String {
    val cal = Calendar.getInstance()
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    cal.time = Date()
    cal.add(Calendar.DATE, 1)
    return format.format(cal.time)
}

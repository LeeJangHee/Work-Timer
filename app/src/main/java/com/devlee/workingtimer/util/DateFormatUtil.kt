package com.devlee.workingtimer.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatUtil {
    private val yearMonthDayFormatter = SimpleDateFormat("yyyy. MM. dd", Locale.ROOT)
    private val monthFormatter = SimpleDateFormat("MM", Locale.ROOT)
    private val yearFormatter = SimpleDateFormat("yyyy", Locale.ROOT)
    private val yearMonthFormatter = SimpleDateFormat("yyyy. MM", Locale.ROOT)
    private val yearMonthDayTimeFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.ROOT)
    private val time24Formatter = SimpleDateFormat("HH:mm:ss", Locale.ROOT)


    fun Long.toStringTime()  = getTimer(this)

    fun getTimer(time: Long): String {
        return time24Formatter.format(time)
    }

    fun yearMonthDayTime(time: Long): String {
        return yearMonthDayTimeFormatter.format(time)
    }
}
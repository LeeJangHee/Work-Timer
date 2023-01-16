package com.devlee.workingtimer

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat", "NewApi")
object DateFormatUtil {
    private val DEFAULT_DATE_FORMAT = SimpleDateFormat("yyyy. MM. dd")
    private val fullDate = DateTimeFormatter.ofPattern("yyyy. MM. dd")
    private val monthFormatter = DateTimeFormatter.ofPattern("MM")
    private val yearFormatter = DateTimeFormatter.ofPattern("yyyy")
    private val yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy. MM")
    private val yearMonthDayTimeFormatter = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())


    fun Long.toStringTime()  = getTimer(this)

    fun getTodayDate(): String {
        return getAllDate(LocalDate.now())
    }

    fun getAllDate(date: LocalDate): String {
        return fullDate.format(date)
    }

    fun getTimer(time: Long): String {
        val timeFormatter = "hh:mm:ss"
        val dateFormat = SimpleDateFormat(timeFormatter)
        return dateFormat.format(time)
    }

    fun yearMonthDayTime(time: Long): String {
        return yearMonthDayTimeFormatter.format(time)
    }
}
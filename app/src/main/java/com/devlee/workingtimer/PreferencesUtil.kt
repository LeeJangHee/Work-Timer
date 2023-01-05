package com.devlee.workingtimer

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

object PreferencesUtil {

    private const val PREF_NAME = "SharedPreferencesUtil"
    private const val END_TIME_KEY = "end_time"
    private const val WORKING_TIME_KEY = "working_time"


    private lateinit var pref: SharedPreferences
    private val gson = Gson()


    fun init(context: Context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun setEndTime(endTime: Long = 0L) {
        pref.edit {
            putLong(END_TIME_KEY, endTime)
        }
    }

    fun getEndTime(): Long {
        return pref.getLong(END_TIME_KEY, 0L)
    }

    fun removeEndTime() {
        pref.edit {
            remove(END_TIME_KEY)
        }
    }

    fun setWorkingTime(hourStr: String) {
        val workingTime = if (hourStr.isNotEmpty()) hourStr.toInt().hour else 0L
        pref.edit {
            putLong(WORKING_TIME_KEY, workingTime)
        }
    }

    fun getWorkingTime(): Long {
        return pref.getLong(WORKING_TIME_KEY, 0L)
    }

}
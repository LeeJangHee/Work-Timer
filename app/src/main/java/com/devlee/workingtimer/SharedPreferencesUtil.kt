package com.devlee.workingtimer

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object SharedPreferencesUtil {

    private const val PREF_NAME = "SharedPreferencesUtil"
    private const val END_TIME_KEY = "end_time"


    private lateinit var pref: SharedPreferences
    private val gson = Gson()


    fun init(context: Context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun setEndTime(endTime: Long = 0L) {
        val edit = pref.edit()
        edit.putLong(END_TIME_KEY, endTime)
        edit.apply()
    }

    fun getEndTime(): Long {
        return pref.getLong(END_TIME_KEY, 0L)
    }

    fun removeEndTime() {
        val edit = pref.edit()
        edit.remove(END_TIME_KEY)
        edit.commit()
    }

}
package com.devlee.workingtimer

import android.util.Log
import android.view.View
import com.devlee.workingtimer.Consts.HOUR
import com.devlee.workingtimer.Consts.MINUTE
import com.devlee.workingtimer.Consts.SECOND
import com.devlee.workingtimer.Consts.TAG

fun View.show() {
    visibility = View.VISIBLE
}
fun View.hide() {
    visibility = View.INVISIBLE
}
fun View.gone() {
    visibility = View.GONE
}

val Int.second: Long
    get() = this * SECOND
val Int.minute: Long
    get() = this * MINUTE
val Int.hour: Long
    get() = this * HOUR
val now: Long
    get() = System.currentTimeMillis()


fun sleep(time: Long) {
    try {
        Thread.sleep(time)
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e(TAG, "sleep: ${e.localizedMessage}", e)
    }
}
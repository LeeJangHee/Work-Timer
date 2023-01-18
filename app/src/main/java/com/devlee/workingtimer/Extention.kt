package com.devlee.workingtimer

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
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

fun Long.toHour() = this / HOUR

fun sleep(time: Long) {
    try {
        Thread.sleep(time)
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e(TAG, "sleep: ${e.localizedMessage}", e)
    }
}

fun Activity.turnScreenOnAndKeyguardOff() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }

    with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
        requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
    }
}
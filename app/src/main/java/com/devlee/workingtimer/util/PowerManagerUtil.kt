package com.devlee.workingtimer.util

import android.content.Context
import android.os.PowerManager
import com.devlee.workingtimer.App
import com.devlee.workingtimer.second

object PowerManagerUtil {

    private lateinit var wakeLock: PowerManager.WakeLock

    fun create(context: Context) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val flags = PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP
        wakeLock = pm.newWakeLock(flags, App::class.java.simpleName+"_tag")
    }


    fun acquire() {
        wakeLock.acquire(30.second)
    }

    fun release() {
        if (wakeLock.isHeld) {
            wakeLock.release()
        }
    }
}
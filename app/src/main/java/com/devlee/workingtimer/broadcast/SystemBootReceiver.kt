package com.devlee.workingtimer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devlee.workingtimer.util.AlarmManagerUtl
import com.devlee.workingtimer.util.AlarmManagerUtl.initAlarmClock

class SystemBootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                val alarMgr = AlarmManagerUtl.createAlarmManager(context)
                alarMgr.initAlarmClock(context)
            }
        }
    }
}
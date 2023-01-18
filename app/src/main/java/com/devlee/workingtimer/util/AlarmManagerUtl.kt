package com.devlee.workingtimer.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.devlee.workingtimer.broadcast.NotificationReceiver
import com.devlee.workingtimer.now
import com.devlee.workingtimer.second
import com.devlee.workingtimer.util.AlarmManagerUtl.initAlarmClock
import com.devlee.workingtimer.view.AlarmActivity

object AlarmManagerUtl {

    fun createAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun AlarmManager.initAlarmClock(context: Context) {
        val alarmIntent = Intent(context, NotificationReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
//        val clockIntent = Intent(context, AlarmActivity::class.java).let { intent ->
//            PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
//        }
        val endTime = PreferencesUtil.getEndTime()
        this.setAlarmClock(AlarmManager.AlarmClockInfo(endTime, alarmIntent), alarmIntent)
    }

    fun Context.getAlarmIntent(): PendingIntent {
        return Intent(this, NotificationReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
    }

    fun AlarmManager.testAlarmClock(context: Context) {
        val alarmIntent = Intent(context, NotificationReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        val clockIntent = Intent(context, AlarmActivity::class.java).let { intent ->
            PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }
        this.setAlarmClock(AlarmManager.AlarmClockInfo(now + 30.second, clockIntent), alarmIntent)
    }
}
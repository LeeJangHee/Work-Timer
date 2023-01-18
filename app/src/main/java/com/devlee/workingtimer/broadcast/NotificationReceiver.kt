package com.devlee.workingtimer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devlee.workingtimer.util.AudioUtil
import com.devlee.workingtimer.util.NotificationUtil
import com.devlee.workingtimer.util.PowerManagerUtil
import com.devlee.workingtimer.view.AlarmActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        intent?.let {
            PowerManagerUtil.acquire()
            NotificationUtil.notify(context)
            AudioUtil.startAudio()
//            context.startActivity(Intent(context, AlarmActivity::class.java).apply {
//                this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            })
        }
    }
}
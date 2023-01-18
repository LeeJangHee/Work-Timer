package com.devlee.workingtimer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devlee.workingtimer.Consts.ACTION_STOP_ALARM_SOUND
import com.devlee.workingtimer.util.AudioUtil
import com.devlee.workingtimer.util.NotificationUtil

class NotiActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (context != null) {
                when (it.action) {
                    ACTION_STOP_ALARM_SOUND -> {
                        // 알림 소리 끄기
                        AudioUtil.stopAudio()
                        NotificationUtil.updateStopAudio(context)
                    }
                }
            }
        }
    }
}
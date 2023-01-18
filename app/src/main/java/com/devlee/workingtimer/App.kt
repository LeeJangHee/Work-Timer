package com.devlee.workingtimer

import android.app.Application
import com.devlee.workingtimer.util.AudioUtil
import com.devlee.workingtimer.util.NotificationUtil
import com.devlee.workingtimer.util.PowerManagerUtil
import com.devlee.workingtimer.util.PreferencesUtil


class App : Application() {

    companion object {
        @Volatile
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App =
            instance ?: synchronized(this) {
                instance ?: App().also {
                    instance = it
                }
            }
    }

    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        instance = this
        AudioUtil.initAudioPlayer(this)
        NotificationUtil.create(this)
        PowerManagerUtil.create(this)
    }
}
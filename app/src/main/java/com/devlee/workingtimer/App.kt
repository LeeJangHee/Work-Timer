package com.devlee.workingtimer

import android.app.Application


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
    }
}
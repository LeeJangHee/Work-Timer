package com.devlee.workingtimer.view

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.devlee.workingtimer.NotificationUtil

class TimerWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        return try {

            NotificationUtil.create(applicationContext)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
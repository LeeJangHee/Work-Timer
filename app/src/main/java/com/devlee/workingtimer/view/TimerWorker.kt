package com.devlee.workingtimer.view

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.devlee.workingtimer.NotificationUtil

class TimerWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val workerContext = context

    override fun doWork(): Result {
        return try {

            NotificationUtil.create(workerContext)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
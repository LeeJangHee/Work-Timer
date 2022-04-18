package com.devlee.workingtimer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.devlee.workingtimer.view.TimerWorker
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        intent?.let {
            val workRequest = OneTimeWorkRequestBuilder<TimerWorker>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 5000, TimeUnit.MILLISECONDS)
                .build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(workRequest)
        }
    }
}
package com.devlee.workingtimer.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.devlee.workingtimer.Consts
import com.devlee.workingtimer.Consts.ACTION_STOP_ALARM_SOUND
import com.devlee.workingtimer.Consts.CHANNEL_ID
import com.devlee.workingtimer.R
import com.devlee.workingtimer.broadcast.NotiActionReceiver
import com.devlee.workingtimer.util.DateFormatUtil.toStringTime

object NotificationUtil {

    private val channel = NotificationChannel(Consts.CHANNEL_ID, Consts.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)

    private lateinit var notification: Notification
    @SuppressLint("StaticFieldLeak")
    private lateinit var notificationCompatBuilder: NotificationCompat.Builder

    fun create(context: Context) {
        // Register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)

        val endTime = PreferencesUtil.getEndTime().toStringTime()
        val notificationClickIntent = PendingIntent.getActivity(
            context,
            0,
            context.packageManager.getLaunchIntentForPackage(context.packageName),
            PendingIntent.FLAG_IMMUTABLE
        )

        notificationCompatBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("퇴큰 알림")
            .setContentText("퇴근 시간입니다!!!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(endTime)
            )
            .setChannelId(CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)
            .setContentIntent(notificationClickIntent)
            .addAction(R.mipmap.ic_launcher, "소리 끄기", context.createActionIntent(ACTION_STOP_ALARM_SOUND))

        notification = notificationCompatBuilder.build()
    }

    @SuppressLint("RestrictedApi")
    fun updateStopAudio(context: Context) {

        val actions = notificationCompatBuilder.mActions.filter { it.actionIntent != context.createActionIntent(ACTION_STOP_ALARM_SOUND) }

        notification = NotificationCompat.Builder(context, notification)
            .clearActions()
            .apply {
                actions.forEach {
                    addAction(it)
                }
            }
            .build()
        NotificationManagerCompat.from(context).notify(0, notification)
    }

    fun notify(context: Context) {
        NotificationManagerCompat.from(context).notify(0, notification)
    }

    fun clear(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }

    private fun Context.createActionIntent(action: String?): PendingIntent {
        return Intent(this, NotiActionReceiver::class.java).let {
            it.action = action
            PendingIntent.getBroadcast(this, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }
    }
}
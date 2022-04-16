package com.devlee.workingtimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {

    private val channel = NotificationChannel(Consts.CHANNEL_ID, Consts.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)

    fun create(context: Context) {
        // Register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(context, Consts.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("퇴큰 알림")
            .setContentText("퇴근 시간입니다 !!!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("퇴근 시간입니다 !!!"))
            .setChannelId(Consts.CHANNEL_ID)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(0, notificationBuilder)
        }
    }

    fun clear(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }
}
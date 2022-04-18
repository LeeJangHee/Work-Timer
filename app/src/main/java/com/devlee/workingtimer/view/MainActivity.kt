package com.devlee.workingtimer.view

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devlee.workingtimer.SharedPreferencesUtil
import com.devlee.workingtimer.broadcast.BootReceiver
import com.devlee.workingtimer.databinding.ActivityMainBinding
import com.devlee.workingtimer.hour
import com.devlee.workingtimer.now
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmIntent: PendingIntent
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            endTime = SharedPreferencesUtil.getEndTime()
        }
        setContentView(binding.root)

        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiver = ComponentName(this, BootReceiver::class.java)
        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            PackageManager.DONT_KILL_APP
        )

        alarmIntent = Intent(this, BootReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        binding.button.setOnClickListener {
            if (SharedPreferencesUtil.getEndTime() == 0L) {
                SharedPreferencesUtil.setEndTime(now + 8.hour)
                alarmMgr.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    now + 8.hour,
                    alarmIntent
                )
            } else {
                SharedPreferencesUtil.removeEndTime()
                alarmMgr.cancel(alarmIntent)
            }
            viewModel.getEndTimeFlow(SharedPreferencesUtil.getEndTime())

        }

        lifecycleScope.launchWhenResumed {
            viewModel.timerFlow.collect {
                binding.endTime = it
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
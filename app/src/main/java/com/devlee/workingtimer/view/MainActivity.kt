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
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.devlee.workingtimer.NotificationUtil
import com.devlee.workingtimer.PreferencesUtil
import com.devlee.workingtimer.broadcast.BootReceiver
import com.devlee.workingtimer.databinding.ActivityMainBinding
import com.devlee.workingtimer.now
import com.devlee.workingtimer.toHour
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmIntent: PendingIntent
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            endTime = PreferencesUtil.getEndTime()
            workingTime = PreferencesUtil.getWorkingTime().toHour()
        }
        setContentView(binding.root)
        binding.executePendingBindings()

        if (now >= PreferencesUtil.getEndTime()) {
            PreferencesUtil.removeEndTime()
            binding.endTime = 0L
        }
        NotificationUtil.clear(this)

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
            val alarmTime = now + PreferencesUtil.getWorkingTime()
            if (PreferencesUtil.getEndTime() == 0L) {
                PreferencesUtil.setEndTime(alarmTime)
                alarmMgr.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    alarmIntent
                )
            } else {
                PreferencesUtil.removeEndTime()
                alarmMgr.cancel(alarmIntent)
            }
            viewModel.getEndTimeFlow(PreferencesUtil.getEndTime())

        }

        binding.workingTimeEdit.doAfterTextChanged { editable ->
            editable?.let {
                PreferencesUtil.setWorkingTime(it.toString())
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.timerFlow.collect {
                binding.endTime = it
                binding.workingTimeEdit.isEnabled = it <= 0
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
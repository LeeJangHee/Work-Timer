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
import com.devlee.workingtimer.util.NotificationUtil
import com.devlee.workingtimer.util.PreferencesUtil
import com.devlee.workingtimer.broadcast.NotificationReceiver
import com.devlee.workingtimer.databinding.ActivityMainBinding
import com.devlee.workingtimer.now
import com.devlee.workingtimer.second
import com.devlee.workingtimer.toHour
import com.devlee.workingtimer.util.AlarmManagerUtl
import com.devlee.workingtimer.util.AlarmManagerUtl.getAlarmIntent
import com.devlee.workingtimer.util.AlarmManagerUtl.initAlarmClock
import com.devlee.workingtimer.util.AlarmManagerUtl.testAlarmClock
import com.devlee.workingtimer.util.PowerManagerUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            endTime = PreferencesUtil.getEndTime()
            workingTime = PreferencesUtil.getWorkingTime().toHour()
            lastTime = PreferencesUtil.getLastTme()
        }
        setContentView(binding.root)
        binding.executePendingBindings()

        if (now >= PreferencesUtil.getEndTime()) {
            PreferencesUtil.removeEndTime()
            binding.endTime = 0L
        }

        NotificationUtil.clear(this)
        PowerManagerUtil.release()

        val alarmMgr = AlarmManagerUtl.createAlarmManager(this)

        binding.button.setOnClickListener {
            val alarmTime = now + PreferencesUtil.getWorkingTime()
            if (PreferencesUtil.getEndTime() == 0L) {
                PreferencesUtil.setEndTime(alarmTime)
                alarmMgr.initAlarmClock(this)
//                alarmMgr.testAlarmClock(this)
            } else {
                PreferencesUtil.removeEndTime()
                alarmMgr.cancel(this.getAlarmIntent())
            }
            viewModel.getEndTimeFlow(PreferencesUtil.getEndTime())
        }

        binding.workingTimeEdit.doAfterTextChanged { editable ->
            editable?.let {
                PreferencesUtil.setWorkingTime(it.toString())
            }
        }

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.timerFlow.collect {
                    binding.endTime = it
                    binding.workingTimeEdit.isEnabled = it <= 0
                    if (it > 0) {
                        viewModel.setLastTimeFLow(it)
                    }
                }
            }

            launch {
                viewModel.lastTimeFlow.collect {
                    binding.lastTime = it
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
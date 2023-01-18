package com.devlee.workingtimer.view

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.devlee.workingtimer.util.PreferencesUtil
import com.devlee.workingtimer.R
import com.devlee.workingtimer.databinding.ActivityAlarmBinding
import com.devlee.workingtimer.turnScreenOnAndKeyguardOff
import com.devlee.workingtimer.util.AudioUtil
import com.devlee.workingtimer.util.DateFormatUtil
import kotlinx.coroutines.NonCancellable.start

class AlarmActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = ActivityAlarmBinding.inflate(layoutInflater).apply {
            time = DateFormatUtil.yearMonthDayTime(PreferencesUtil.getEndTime())
        }
        setContentView(binding.root)
        binding.executePendingBindings()

        AudioUtil.startAudio()

        binding.clearBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        turnScreenOnAndKeyguardOff()

    }

    override fun onDestroy() {
        AudioUtil.stopAudio()
        super.onDestroy()
    }
}
package com.devlee.workingtimer.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.devlee.workingtimer.PreferencesUtil
import com.devlee.workingtimer.databinding.ActivityAlarmBinding

class AlarmActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = ActivityAlarmBinding.inflate(layoutInflater).apply {
            time = PreferencesUtil.getEndTime()
        }
        setContentView(binding.root)
        binding.executePendingBindings()

        binding.clearBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}
package com.devlee.workingtimer.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devlee.workingtimer.*
import com.devlee.workingtimer.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            endTime = SharedPreferencesUtil.getEndTime()
        }
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (SharedPreferencesUtil.getEndTime() == 0L) {
                SharedPreferencesUtil.setEndTime(now + 7.hour)
            } else {
                SharedPreferencesUtil.removeEndTime()
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
package com.devlee.workingtimer.view

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.devlee.workingtimer.Consts.ENQUEUE_WORK_NAME
import com.devlee.workingtimer.PreferencesUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {

    private val workManager = WorkManager.getInstance(application)

    private var timerMutableFlow = MutableStateFlow(PreferencesUtil.getEndTime())
    val timerFlow = timerMutableFlow.asStateFlow()

    private var _lastTimeMutableFlow = MutableStateFlow(PreferencesUtil.getLastTme())
    val lastTimeFlow = _lastTimeMutableFlow.asStateFlow()

    @SuppressLint("EnqueueWork")
    fun applyTimer() {
        val continuation = workManager
            .beginUniqueWork(
                ENQUEUE_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequest.from(TimerWorker::class.java)
            )

        continuation.enqueue()
    }

    fun getEndTimeFlow(time: Long) = viewModelScope.launch {
        timerMutableFlow.emit(time)
    }

    fun setLastTimeFLow(last: Long) = viewModelScope.launch {
        PreferencesUtil.setLastTime(last)
        _lastTimeMutableFlow.emit(last)
    }



}

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
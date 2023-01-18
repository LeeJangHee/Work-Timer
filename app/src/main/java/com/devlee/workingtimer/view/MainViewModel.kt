package com.devlee.workingtimer.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devlee.workingtimer.util.PreferencesUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {

    private var timerMutableFlow = MutableStateFlow(PreferencesUtil.getEndTime())
    val timerFlow = timerMutableFlow.asStateFlow()

    private var _lastTimeMutableFlow = MutableStateFlow(PreferencesUtil.getLastTme())
    val lastTimeFlow = _lastTimeMutableFlow.asStateFlow()


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
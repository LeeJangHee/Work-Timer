package com.devlee.workingtimer

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("buttonText")
fun Button.setButtonText(endTime: Long) {
    if (endTime == 0L) {
        text = context.getString(R.string.working_start_title)
    } else {
        text = context.getString(R.string.working_end_title)
    }
}

@BindingAdapter("timerText")
fun TextView.setTimerText(endTime: Long) {
    if (endTime == 0L) {
        text = "출근 버튼을 클릭해주세요."
    } else {
        text = DateFormatUtil.getTimer(endTime)
    }
}
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

@BindingAdapter("workingTimeText")
fun TextView.setWorkingTimeText(workingTime: Long) {
    text = if (workingTime <= 0) {
        null
    } else {
        workingTime.toString()
    }
}

@BindingAdapter("lastTimeText")
fun TextView.setLastTimeText(lastTime: Long) {
    text = if (lastTime == 0L) {
        "최근 저장된 시간이 없습니다."
    } else {
        "최근 저장된 퇴근시간\n${DateFormatUtil.yearMonthDayTime(lastTime)}"
    }
}
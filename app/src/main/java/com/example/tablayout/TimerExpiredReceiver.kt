package com.example.tablayout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(Timer.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0,context)
    }
}
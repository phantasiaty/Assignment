package com.example.tablayout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class TimerNotificationActionReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            AppConstant.ACTION_STOP ->{
                Timer.removeAlaram(context)
                PrefUtil.setTimerState(Timer.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstant.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = Timer.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                Timer.removeAlaram(context)
                PrefUtil.setTimerState(Timer.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstant.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondRemaining(context)
                val wakeUpTime = Timer.setAlarm(context,Timer.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(Timer.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context,wakeUpTime)
            }
            AppConstant.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = Timer.setAlarm(context,Timer.nowSeconds,secondsRemaining)
                PrefUtil.setTimerState(Timer.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining,context)
                NotificationUtil.showTimerRunning(context,wakeUpTime)
            }
        }
    }
}
package com.example.bid

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.IBinder

class CountdownService : Service() {

    companion object {
        const val EXTRA_START_TIME = "EXTRA_START_TIME"
    }

    private var countDownTimer: CountDownTimer? = null
    private var timeLeft: Long = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("CountdownPrefs", Context.MODE_PRIVATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startTime = intent?.getLongExtra(EXTRA_START_TIME, 0L) ?: 0L
        timeLeft = sharedPreferences.getLong("timeLeft", startTime)

        if (timeLeft == 0L) {
            stopSelf()
            return START_NOT_STICKY
        }

        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                sharedPreferences.edit().putLong("timeLeft", timeLeft).apply()
            }

            override fun onFinish() {
                stopSelf()
            }
        }

        countDownTimer?.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        sharedPreferences.edit().remove("timeLeft").apply()
    }
}

package com.example.trainingtimer

import android.app.usage.UsageEvents.Event
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    val onStartTimer = MutableLiveData<Event>()

    var timerStr = MutableLiveData<String>().apply{
        value = getTimerString()
    }

    private var timer : Int = 0

    private fun getTimerString():String{
        var retStr = timer.toString()

        if(timer < 10){
            retStr = "0$timer"
        }

        return retStr
    }

    fun onClickStart(view: View){
        onStartTimer.value = Event()
    }

}
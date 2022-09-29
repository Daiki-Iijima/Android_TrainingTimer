package com.example.trainingtimer

import android.app.usage.UsageEvents.Event
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    val onStartTimer = MutableLiveData<Event>()
    val onStopTimer = MutableLiveData<Event>()

    var isStart:Boolean = false
    set(value) {
        if(field == value){
            return
        }

        //  値をマイナスにさせない
        if(value && timer == 0){
            return
        }

        timerBtnText.postValue(getTimerBtnText(value))

        if(value){
            onStartTimer.postValue(Event())
        }else {
            onStopTimer.postValue(Event())
        }

        field = value
    }
    private var timer : Int = 0

    private lateinit var timerSetting:TimerSetting

    var timerBtnText = MutableLiveData<String>().apply {
        value = getTimerBtnText(isStart)
    }

    var timerStr = MutableLiveData<String>().apply{
        value = getTimerString(timer)
    }

    fun loadSetting(setting:SettingViewModel){
        timerSetting = setting.timerSetting

        //  TODO : ほかの時間も計算する必要がある
        setTime(timerSetting.TrainingTime)
    }

    private fun getTimerString(value:Int):String{
        var retStr = value.toString()

        if(value < 10){
            retStr = "0$value"
        }

        return retStr
    }

    fun setTime(value:Int){
        timer = value
        timerStr.postValue(getTimerString(timer))
    }

    fun resetTimer(){
        //  TODO : ほかの時間も計算する必要がある
        timer = timerSetting.TrainingTime
        timerStr.postValue(getTimerString(timer))
    }

    fun addTimer(value:Int){
        timer += value
        timerStr.postValue(getTimerString(timer))
    }

    fun getTimer():Int{
        return timer
    }

    fun onClickTimer(view: View) {
        isStart = !isStart
    }

    private fun getTimerBtnText(flag:Boolean):String{
        return if(flag) "タイマーストップ" else "タイマースタート"
    }

}
package com.example.trainingtimer

import android.app.usage.UsageEvents.Event
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    val onStartTimer = MutableLiveData<Event>()
    val onStopTimer = MutableLiveData<Event>()

    //  現在のカウント状態
    var status: ConfigSettingType = ConfigSettingType.StartWaitTime
    var repeatCount: Int = 0

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

    var statusStr = MutableLiveData<String>().apply{
        value = getStatusString(status)
    }

    var repeatCountStr = MutableLiveData<String>().apply{
        value = "0/0"
    }

    fun loadSetting(setting:SettingViewModel){
        timerSetting = setting.timerSetting

        //  TODO : ほかの時間も計算する必要がある
        setTime(timerSetting.StartWaitTime)

        //  繰り返しカウントの表示
        repeatCountStr.postValue("${repeatCount+1}/${timerSetting.RepeatCount}")
    }

    private fun getTimerString(value:Int):String{
        var retStr = value.toString()

        if(value < 10){
            retStr = "0$value"
        }

        return retStr
    }

    private fun getStatusString(st:ConfigSettingType):String{
        return when(st){
            ConfigSettingType.StartWaitTime->{
                "トレーニング準備"
            }
            ConfigSettingType.TrainingTime->{
                "トレーニング"
            }
            ConfigSettingType.RestTime->{
                "休憩"
            }
            else->{
                "不正な状態"
            }
        }
    }

    fun setTime(value:Int){
        timer = value
        timerStr.postValue(getTimerString(timer))
    }

    fun resetTimer(){
        //  TODO : ほかの時間も計算する必要がある
        var setTime:Int = 0
        when(status){
            ConfigSettingType.StartWaitTime->{
                setTime = timerSetting.StartWaitTime
            }
            ConfigSettingType.TrainingTime->{
                setTime = timerSetting.TrainingTime
            }
            ConfigSettingType.RestTime->{
                setTime = timerSetting.RestTime
            }
            else->{
            }
        }
        timer = setTime
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

    fun nextStep(){
        //  タイマーが0の場合のみ次の状態に行く
        if(timer != 0){
            return
        }

        when(status){
            ConfigSettingType.StartWaitTime->{
                status = ConfigSettingType.TrainingTime
            }
            ConfigSettingType.TrainingTime->{
                status = ConfigSettingType.RestTime
            }
            ConfigSettingType.RestTime->{
                //  リピートできるかチェック
                if(checkCanRepeat()){
                    status = ConfigSettingType.TrainingTime
                    repeatCount++
                    //  繰り返しカウントの更新
                    repeatCountStr.postValue("${repeatCount+1}/${timerSetting.RepeatCount}")
                }else {
                    status = ConfigSettingType.StartWaitTime
                    isStart = false
                }

            }
            else->{
                return
            }
        }

        //  状態の表示を更新
        statusStr.postValue(getStatusString(status))


        //  時間を再設定
        resetTimer()
    }

    private fun checkCanRepeat():Boolean{
        return repeatCount+1 < timerSetting.RepeatCount
    }

}
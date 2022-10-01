package com.example.trainingtimer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

    val timerSetting = TimerSetting(0,0,0,0)

    private val _onTransit = MutableLiveData<Boolean>()

    val onTransit : LiveData<Boolean>
        get() = _onTransit

    fun navigateToTransitHandled() {
        _onTransit.value = false
    }

    var startWaitTimeStr = MutableLiveData<String>().apply {
        value = timerSetting.getConvertStr(ConfigSettingType.StartWaitTime)
    }
    var trainingTimeStr = MutableLiveData<String>().apply {
        value = timerSetting.getConvertStr(ConfigSettingType.TrainingTime)
    }
    var restTimeStr = MutableLiveData<String>().apply {
        value = timerSetting.getConvertStr(ConfigSettingType.RestTime)
    }
    var repeatCountStr = MutableLiveData<String>().apply {
        value = timerSetting.getConvertStr(ConfigSettingType.RepeatCount)
    }

    fun changeSetting(v: View, value: Int) {
        timerSetting.changeValue(v.tag as ConfigSettingType, value)
        updateStr(timerSetting)
    }

    fun onClickConfirm(view:View){
        //  イベントを発火
        _onTransit.value = true
    }

    private fun updateStr(setting: TimerSetting) {
        startWaitTimeStr.postValue(setting.getConvertStr(ConfigSettingType.StartWaitTime))
        trainingTimeStr.postValue(setting.getConvertStr(ConfigSettingType.TrainingTime))
        restTimeStr.postValue(setting.getConvertStr(ConfigSettingType.RestTime))
        repeatCountStr.postValue(setting.getConvertStr(ConfigSettingType.RepeatCount))
    }
}
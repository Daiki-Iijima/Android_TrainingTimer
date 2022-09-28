package com.example.trainingtimer

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

    private val timerSetting = TimerSetting(0,0,0,0)

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

    private fun updateStr(setting: TimerSetting) {
        startWaitTimeStr.postValue(setting.getConvertStr(ConfigSettingType.StartWaitTime))
        trainingTimeStr.postValue(setting.getConvertStr(ConfigSettingType.TrainingTime))
        restTimeStr.postValue(setting.getConvertStr(ConfigSettingType.RestTime))
        repeatCountStr.postValue(setting.getConvertStr(ConfigSettingType.RepeatCount))
    }
}
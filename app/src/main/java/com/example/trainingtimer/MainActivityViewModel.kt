package com.example.trainingtimer

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ConfigSettingType {
    StartWaitTime,
    TrainingTime,
    RestTime,
    RepeatCount,
}

class MainActivityViewModel : ViewModel() {


    var StartWaitTimeStr = MutableLiveData<String>().apply {
        value = "00:00"
    }
    var TrainingTimeStr = MutableLiveData<String>().apply {
        value = "00:00"
    }
    var RestTimeStr = MutableLiveData<String>().apply {
        value = "00:00"
    }
    var RepeatCountStr = MutableLiveData<String>().apply {
        value = "00"
    }

    private val timerSetting = TimerSetting(0, 0, 0, 0)

    fun changeSetting(v: View, value: Int) {

        //  UIに紐づけたタグからどの値が対象かを判別
        val type = v.tag

        when (type) {
            ConfigSettingType.StartWaitTime -> {
                //  時間がマイナスにならないようにする
                if (timerSetting.StartWaitTime == 0 && value < 0) {
                    return
                }

                timerSetting.StartWaitTime += value

                StartWaitTimeStr.postValue(convertSecTimeToMinSecTime(timerSetting.StartWaitTime))
            }
            ConfigSettingType.TrainingTime -> {
                //  時間がマイナスにならないようにする
                if (timerSetting.TrainingTime == 0 && value < 0) {
                    return
                }

                timerSetting.TrainingTime += value

                TrainingTimeStr.postValue(convertSecTimeToMinSecTime(timerSetting.TrainingTime))
            }
            ConfigSettingType.RestTime -> {
                //  時間がマイナスにならないようにする
                if (timerSetting.RestTime == 0 && value < 0) {
                    return
                }

                timerSetting.RestTime += value

                RestTimeStr.postValue(convertSecTimeToMinSecTime(timerSetting.RestTime))
            }
            ConfigSettingType.RepeatCount -> {
                //  時間がマイナスにならないようにする
                if (timerSetting.RepeatCount == 0 && value < 0) {
                    return
                }

                timerSetting.RepeatCount += value

                var setStr = "0${timerSetting.RepeatCount}"

                if (timerSetting.RepeatCount >= 10) {
                    setStr = timerSetting.RepeatCount.toString()
                }

                RepeatCountStr.postValue(setStr)
            }
        }
    }

    //  内部では秒で値を保持しているので、分と秒の数値に変換する
    private fun convertSecTimeToMinSecTime(time: Int): String {
        val retMin = time / 60
        val retSec = time - (retMin * 60)

        var retMinStr = "0$retMin"
        if (retMin >= 10) {
            retMinStr = "$retMin"
        }

        var retSecStr = "0$retSec"
        if (retSec >= 10) {
            retSecStr = "$retSec"
        }

        return "$retMinStr:$retSecStr"
    }
}
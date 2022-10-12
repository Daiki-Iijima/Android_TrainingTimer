package com.example.trainingtimer

//  データ
//  StartWaitTime : タイマー開始までの時間
//  TrainingTime : トレーニングする時間
//  RestTime : 休憩時間
//  RepeatCount : 何回繰り返すか
class TimerSetting(var StartWaitTime:Int,var TrainingTime:Int,var RestTime:Int,var RepeatCount:Int){
    fun changeValue(type: ConfigSettingType, value: Int) {

        //  UIに紐づけたタグからどの値が対象かを判別
        when (type) {
            ConfigSettingType.StartWaitTime -> {
                //  時間がマイナスにならないようにする
                if (StartWaitTime == 0 && value < 0) {
                    return
                }

                StartWaitTime += value
            }
            ConfigSettingType.TrainingTime -> {
                //  時間がマイナスにならないようにする
                if (TrainingTime == 0 && value < 0) {
                    return
                }

                TrainingTime += value
            }
            ConfigSettingType.RestTime -> {
                //  時間がマイナスにならないようにする
                if (RestTime == 0 && value < 0) {
                    return
                }

                RestTime += value
            }
            ConfigSettingType.RepeatCount -> {
                //  時間がマイナスにならないようにする
                if (RepeatCount == 0 && value < 0) {
                    return
                }

                RepeatCount += value
            }
        }
    }

    fun getConvertStr(type:ConfigSettingType):String{
        when (type) {
            ConfigSettingType.StartWaitTime -> {
                return convertSecTimeToMinSecTime(StartWaitTime)
            }
            ConfigSettingType.TrainingTime -> {
                return convertSecTimeToMinSecTime(TrainingTime)
            }
            ConfigSettingType.RestTime -> {
                return convertSecTimeToMinSecTime(RestTime)
            }
            ConfigSettingType.RepeatCount -> {
                var setStr = "0${RepeatCount}"

                if (RepeatCount >= 10) {
                    setStr = RepeatCount.toString()
                }

                return setStr
            }
        }
    }

    //  秒を分と秒の数値に変換する
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
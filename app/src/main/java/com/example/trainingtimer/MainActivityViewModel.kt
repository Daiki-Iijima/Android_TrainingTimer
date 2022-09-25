package com.example.trainingtimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel :ViewModel() {
    //  apply value = で初期値を設定
    var startWaitTimeStr = MutableLiveData<String>().apply {
        value = "00:00"
    }

    private var startWaitTimer = 0

    //  内部では秒で値を保持しているので、分と秒の数値に変換する
    private fun getStartWaitTime(time:Int):String{
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

    fun addStartWaitTime(value:Int){

        //  時間がマイナスにならないようにする
        if(startWaitTimer == 0 && value < 0){
            return
        }

        startWaitTimer += value

        startWaitTimeStr.postValue(getStartWaitTime(startWaitTimer))
    }
}
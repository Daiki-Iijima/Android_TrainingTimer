package com.example.trainingtimer

//  データ
//  StartWaitTime : タイマー開始までの時間
//  TrainingTime : トレーニングする時間
//  RestTime : 休憩時間
//  RepeatCount : 何回繰り返すか
data class TimerSetting(var StartWaitTime:Int,var TrainingTime:Int,var RestTime:Int,var RepeatCount:Int)
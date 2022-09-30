package com.example.trainingtimer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.get
import com.example.trainingtimer.databinding.FragmentTimerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TimerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerFragment()
    }

    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel

    var countFlag:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_timer,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = activity?.run {
            ViewModelProvider(this)[TimerViewModel::class.java]
        } ?: throw java.lang.Exception("Invalid activity")

        val settingViewModel = activity?.run {
            ViewModelProvider(this)[SettingViewModel::class.java]
        } ?: throw java.lang.Exception("Invalid activity")

        viewModel.loadSetting(settingViewModel)

        //  別スレッドで無限ループ開始
        Thread {
            while (true) {
                Thread.sleep(1000L)
                if (countFlag) {

                    //  タイマーカウントが終わっていたら
                    if(viewModel.getTimer() == 0){
                        viewModel.nextStep()
                    }else{
                        viewModel.addTimer(-1)
                    }
                }
            }
        }.start()

        viewModel.onStartTimer.observe(viewLifecycleOwner){
            countFlag = true
        }

        viewModel.onStopTimer.observe(viewLifecycleOwner){
            countFlag = false
        }

        binding.viewModel = viewModel

        return binding.root
    }
}
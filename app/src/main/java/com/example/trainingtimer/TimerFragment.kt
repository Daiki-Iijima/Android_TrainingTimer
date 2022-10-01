package com.example.trainingtimer

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.trainingtimer.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerFragment()
    }

    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel

    private var countFlag:Boolean = false
    private var soundCount = 0
    private var soundZero = 0

    private lateinit var soundPool:SoundPool

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

        //  音の設定
        val audioAttribute = AudioAttributes
            .Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        soundPool = SoundPool
            .Builder()
            .setAudioAttributes(audioAttribute)
            .setMaxStreams(2)
            .build()

        //  リソースの割り当て
        soundCount = soundPool.load(context,R.raw.count,1)
        soundZero = soundPool.load(context,R.raw.zero,1)

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

                    //  最後の3秒と0秒目は音を鳴らす
                    if(viewModel.getTimer() == 3){
                        soundPool.play(soundCount,1.0f,1.0f,0,0,1.0f)
                    }
                    if(viewModel.getTimer() == 2){
                        soundPool.play(soundCount,1.0f,1.0f,0,0,1.0f)
                    }
                    if(viewModel.getTimer() == 1){
                        soundPool.play(soundCount,1.0f,1.0f,0,0,1.0f)
                    }
                    if(viewModel.getTimer() == 0){
                        soundPool.play(soundZero,1.0f,1.0f,0,0,1.0f)
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

    override fun onDestroy() {
        //  音声リソースの解放
        soundPool.release()
        super.onDestroy()
    }
}
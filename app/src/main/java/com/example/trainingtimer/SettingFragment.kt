package com.example.trainingtimer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.trainingtimer.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        //  同じアクティビティ上であれば、別のフラグメントからも同じデータを使うことができるようになる
        viewModel = activity?.run {
            ViewModelProvider(this)[SettingViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        binding.viewModel = viewModel

        //  画面遷移イベント
        viewModel.onTransit.observe(viewLifecycleOwner, Observer {
            if(it) {
                viewModel.navigateToTransitHandled()
                onTransit()
            }
        })

        return binding.root
    }

    private fun onTransit(){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.addToBackStack(null)

        transaction.setCustomAnimations(androidx.appcompat.R.anim.abc_slide_in_bottom, androidx.appcompat.R.anim.abc_slide_out_top);

        transaction.replace(R.id.container,TimerFragment.newInstance())
        transaction.commit()
    }

}
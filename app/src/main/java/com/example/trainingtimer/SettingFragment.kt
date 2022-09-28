package com.example.trainingtimer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        return binding.root
    }
}
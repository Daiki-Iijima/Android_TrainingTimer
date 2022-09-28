package com.example.trainingtimer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.get
import com.example.trainingtimer.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerFragment()
    }

    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel

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

        viewModel.onStartTimer.observe(viewLifecycleOwner){
        }

        binding.viewModel = viewModel

        return binding.root
    }
}
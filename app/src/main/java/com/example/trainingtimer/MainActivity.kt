package com.example.trainingtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.trainingtimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val binding : ActivityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding?>(this,R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity  // これがないとうまく動かない
            viewModel = vm
        }
    }
}
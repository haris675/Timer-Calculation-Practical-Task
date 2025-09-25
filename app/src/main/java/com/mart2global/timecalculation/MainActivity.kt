package com.mart2global.timecalculation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mart2global.timecalculation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listOf(binding.btnTimerTask, binding.btnTimeCalculate)
            .forEach { it.setOnClickListener(this) }

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnTimerTask -> {
                startActivity(Intent(this, TimerTaskActivity::class.java))
            }

            binding.btnTimeCalculate -> {
                startActivity(Intent(this, TimeCalculationActivity::class.java))
            }
        }
    }

}
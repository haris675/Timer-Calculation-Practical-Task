package com.mart2global.timecalculation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mart2global.timecalculation.databinding.ActivityTimerTaskBinding
import com.mart2global.timecalculation.fragment.FragmentDashboard
import com.mart2global.timecalculation.fragment.FragmentTimer

class TimerTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityTimerTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(FragmentTimer())
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_timer -> {
                    loadFragment(FragmentTimer())
                    true
                }

                R.id.nav_dashboard -> {
                    loadFragment(FragmentDashboard())
                    true
                }

                else -> {
                    false
                }
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

}
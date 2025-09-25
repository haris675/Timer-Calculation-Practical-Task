package com.mart2global.timecalculation

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mart2global.timecalculation.adapter.TimerCalculationAdapter
import com.mart2global.timecalculation.databinding.ActivityTimerCalculationBinding
import java.util.Calendar

class TimeCalculationActivity : AppCompatActivity() {

    private var startHour = 0
    private var startMinute = 0
    private var startSecond = 0

    private var endHour = 0
    private var endMinute = 0
    private var endSecond = 0

    private val timeList = mutableListOf<String>()

    lateinit var binding: ActivityTimerCalculationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerCalculationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTimes.layoutManager = LinearLayoutManager(this)

        binding.btnStartTime.setOnClickListener { showTimePicker(true) }
        binding.btnEndTime.setOnClickListener { showTimePicker(false) }
        binding.btnGo.setOnClickListener { generateTimesAndSum() }

    }

    private fun showTimePicker(isStart: Boolean) {
        val picker = TimePickerDialog(this, { _, hour, minute ->
            val second = 0 // optional, you can extend to seconds using custom dialog
            if (isStart) {
                startHour = hour
                startMinute = minute
                startSecond = second
                binding.btnStartTime.text = String.format("%02d:%02d:%02d", hour, minute, second)
            } else {
                endHour = hour
                endMinute = minute
                endSecond = second
                binding.btnEndTime.text = String.format("%02d:%02d:%02d", hour, minute, second)
            }
        }, 0, 0, true)
        picker.show()
    }

    private fun generateTimesAndSum() {
        timeList.clear()
        val startCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startHour)
            set(Calendar.MINUTE, startMinute)
            set(Calendar.SECOND, startSecond)
        }
        val endCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, endHour)
            set(Calendar.MINUTE, endMinute)
            set(Calendar.SECOND, endSecond)
        }

        if (endCal.before(startCal)) {
            Toast.makeText(this, "End time must be after start time", Toast.LENGTH_SHORT).show()
            return
        }

        val iterator = startCal.clone() as Calendar
        while (!iterator.after(endCal)) {
            val timeStr = String.format(
                "%02d:%02d:%02d",
                iterator.get(Calendar.HOUR_OF_DAY),
                iterator.get(Calendar.MINUTE),
                iterator.get(Calendar.SECOND)
            )
            timeList.add(timeStr)
            iterator.add(Calendar.SECOND, 1)
        }

        // Populate RecyclerView
        binding.rvTimes.adapter = TimerCalculationAdapter(timeList)

        // Extract unique digits
        //val uniqueDigits = timeList.joinToString("").filter { it.isDigit() }.toSet()
        //val sum = uniqueDigits.map { it.toString().toInt() }.sum()

        val totalSum = timeList.sumOf { time ->
            time.filter { it.isDigit() }  // keep only digits
                .toSet()                  // unique digits for this time
                .sumOf { it.toString().toInt() }  // sum them
        }

        binding.tvUniqueSum.text = "Sum of Unique Digits: $totalSum"
    }

}
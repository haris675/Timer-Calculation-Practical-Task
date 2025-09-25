package com.mart2global.timecalculation.viewmodel

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mart2global.timecalculation.data.InnerTimeData
import com.mart2global.timecalculation.data.TimerData
import com.mart2global.timecalculation.database.TaskDatabase
import com.mart2global.timecalculation.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerTaskViewModelFactory(private val database: TaskDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerTaskViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TimerTaskViewModel(private val database: TaskDatabase) : ViewModel() {

    private val handler = Handler(Looper.getMainLooper())

    private var baseTime = 0L
    private var pauseOffset = 0L
    private var isRunning = false

    private val _time = MutableLiveData("00:00:00")
    val time: LiveData<String> = _time

    private val _timerList = ArrayList<InnerTimeData>()
    private val _timerItem = MutableLiveData(InnerTimeData())
    val timerItem: LiveData<InnerTimeData> = _timerItem

    fun getTimerIsRunning(): Boolean {
        return isRunning
    }

    var _currentElapsedTime = "00:00:00"
    private val updater = object : Runnable {
        override fun run() {
            val elapsed = SystemClock.elapsedRealtime() - baseTime
            _time.value = formatElapsed(elapsed)

            handler.postDelayed(this, 200)
            if (_currentElapsedTime != formatElapsed(elapsed)) {
                Log.e("formatted time : ", "${formatElapsed(elapsed)}")
                _timerItem.value = InnerTimeData(Utils.getCurrentData(), formatElapsed(elapsed))
                _timerList.add(_timerItem.value!!)
            }
            _currentElapsedTime = formatElapsed(elapsed)
        }
    }

    fun start() {
        if (!isRunning) {
            baseTime = SystemClock.elapsedRealtime() - pauseOffset
            handler.post(updater)
            isRunning = true
        }
    }

    fun pause() {
        if (isRunning) {
            handler.removeCallbacks(updater)
            pauseOffset = SystemClock.elapsedRealtime() - baseTime
            isRunning = false
        }
    }

    fun stop() {
        handler.removeCallbacks(updater)
        isRunning = false
        pauseOffset = 0L
        baseTime = 0L
        _time.value = formatElapsed(0L)
        _currentElapsedTime = "00:00:00"
        viewModelScope.launch {
            if (_timerList.isNotEmpty()) {
                database.taskDao().insertData(TimerData(timerList = _timerList))
                delay(750)
            }
            _timerList.clear()
        }
    }

    private fun formatElapsed(ms: Long): String {
        val totalSeconds = ms / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(updater)
    }

}
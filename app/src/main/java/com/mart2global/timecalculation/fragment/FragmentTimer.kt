package com.mart2global.timecalculation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mart2global.timecalculation.adapter.TimerTaskAdapter
import com.mart2global.timecalculation.database.TaskDatabase
import com.mart2global.timecalculation.databinding.FragmentTimerTaskBinding
import com.mart2global.timecalculation.viewmodel.TimerTaskViewModel
import com.mart2global.timecalculation.viewmodel.TimerTaskViewModelFactory

class FragmentTimer : Fragment() {

    lateinit var binding: FragmentTimerTaskBinding
    lateinit var viewModel: TimerTaskViewModel
    lateinit var timerTaskAdapter: TimerTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = TaskDatabase.getDatabase(requireContext())
        val factory = TimerTaskViewModelFactory(database)
        viewModel = ViewModelProvider(this, factory).get(TimerTaskViewModel::class.java)

        timerTaskAdapter = TimerTaskAdapter(ArrayList())
        binding.rvTimer.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = timerTaskAdapter
        }

        viewModel.time.observe(viewLifecycleOwner) {
            binding.txtTimer.text = it
        }
        viewModel.timerItem.observe(viewLifecycleOwner) {
            it.time?.let { innerData ->
                //if (!it.time.isNullOrEmpty())
                timerTaskAdapter.addData(it)
                binding.rvTimer.smoothScrollToPosition(timerTaskAdapter.itemCount - 1)
            }
        }
        clickEvents()
    }

    private fun clickEvents() {
        binding.btnStart.setOnClickListener {
            if (viewModel.getTimerIsRunning()) {
                binding.btnStart.setText("Resume")
                viewModel.pause()
            } else {
                binding.btnStart.setText("Pause")
                viewModel.start()
            }
        }

        binding.btnStop.setOnClickListener {
            binding.btnStart.setText("Start")
            viewModel.stop()
            timerTaskAdapter.setData(ArrayList())
        }
    }

}
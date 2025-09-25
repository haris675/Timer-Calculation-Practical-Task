package com.mart2global.timecalculation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mart2global.timecalculation.adapter.ParentDashboardAdapter
import com.mart2global.timecalculation.database.TaskDao
import com.mart2global.timecalculation.database.TaskDatabase
import com.mart2global.timecalculation.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch

class FragmentDashboard : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    lateinit var dashboardAdapter: ParentDashboardAdapter
    lateinit var taskDao: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskDao = TaskDatabase.getDatabase(requireContext()).taskDao()

        dashboardAdapter = ParentDashboardAdapter(ArrayList())

        binding.rvDasboard.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dashboardAdapter
        }

        lifecycleScope.launch {
            val list = taskDao.getAllTasks()
            if (list.isNotEmpty())
                dashboardAdapter.setData(ArrayList(list))
        }
    }

}
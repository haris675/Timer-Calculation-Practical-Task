package com.mart2global.timecalculation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mart2global.timecalculation.data.TimerData
import com.mart2global.timecalculation.databinding.RowParentItemBinding

class ParentDashboardAdapter(val arrayList: ArrayList<TimerData>) :
    RecyclerView.Adapter<ParentDashboardAdapter.ParentHolder>() {

    fun setData(arrayList: ArrayList<TimerData>) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList)
        notifyDataSetChanged()
    }

    inner class ParentHolder(private val binding: RowParentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TimerData) {
            binding.txtRound.text = "Round " + item.id
            val childAdapter = TimerTaskAdapter(item.timerList)
            binding.rvChild.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvChild.adapter = childAdapter

            if (item.isExpanded) {
                binding.rvChild.visibility = View.VISIBLE
            } else {
                binding.rvChild.visibility = View.GONE
            }

            binding.llTitle.setOnClickListener {
                item.isExpanded = !item.isExpanded
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentHolder {
        return ParentHolder(
            RowParentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ParentHolder, position: Int) {
        holder.bind(arrayList[position])
    }

}
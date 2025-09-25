package com.mart2global.timecalculation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mart2global.timecalculation.data.InnerTimeData
import com.mart2global.timecalculation.databinding.RowDateTimeBinding

class TimerTaskAdapter(private val items: ArrayList<InnerTimeData>) :
    RecyclerView.Adapter<TimerTaskAdapter.TimerTaskHolder>() {

    fun setData(items: ArrayList<InnerTimeData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(items: InnerTimeData) {
        this.items.add(items)
        notifyItemInserted(this.items.size)
    }

    inner class TimerTaskHolder(private val binding: RowDateTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InnerTimeData) {
            with(binding)
            {
                txtDate.text = item.date
                txtTime.text = item.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerTaskHolder {
        return TimerTaskHolder(
            RowDateTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TimerTaskHolder, position: Int) {
        holder.bind(items[position])
    }

}
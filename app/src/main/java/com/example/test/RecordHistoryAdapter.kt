package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecordHistoryAdapter : RecyclerView.Adapter<RecordHistoryAdapter.ViewHolder>() {
    private val dataList: MutableList<ActivityDataClass> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_history_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(newList: List<ActivityDataClass>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTimeTextView: TextView = itemView.findViewById(R.id.dateTimeTextView)
        private val startLocationTextView: TextView = itemView.findViewById(R.id.startLocationTextView)
        private val stepsTextView: TextView = itemView.findViewById(R.id.stepsTextView)
        private val endLocationTextView: TextView = itemView.findViewById(R.id.endLocationTextView)

        fun bind(data: ActivityDataClass) {
            dateTimeTextView.text = data.dateTime
            startLocationTextView.text = data.startLocation
            stepsTextView.text = "Steps: ${data.steps}"
            endLocationTextView.text = "End Location: ${data.endLocation}"
        }
    }
}

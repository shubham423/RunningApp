package com.shubham.runningapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.runningapp.R
import com.shubham.runningapp.databinding.ActivityMainBinding
import com.shubham.runningapp.databinding.ItemRunnBinding
import com.shubham.runningapp.db.Run
import com.shubham.runningapp.other.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.*
import kotlinx.android.synthetic.main.item_runn.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(binding: ItemRunnBinding) : RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val binding =ItemRunnBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return RunViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {

        val run = differ.currentList[position]

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            holder.itemView.tv_date.text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            holder.itemView.tv_speed.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            holder.itemView.tv_distance_covered.text = distanceInKm

            holder.itemView.tv_run_duration.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned}kcal"
            holder.itemView.tvCalorie.text = caloriesBurned
        }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}
















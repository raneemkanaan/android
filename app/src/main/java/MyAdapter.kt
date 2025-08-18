package com.penguin.floor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.penguin.floor.databinding.ItemLayoutBinding

class FloorAdapter(private val floors: List<Floor>) :
    RecyclerView.Adapter<FloorAdapter.FloorViewHolder>() {

    inner class FloorViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        val floor = floors[position]
        holder.binding.floorIdText.text = floor.id.toString()
        holder.binding.venueIdText.text = floor.venueId.toString()
        holder.binding.textViewFloorValue.text = floor.name
    }


    override fun getItemCount(): Int = floors.size
}

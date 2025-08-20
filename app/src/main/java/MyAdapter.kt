package com.penguin.floor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FloorAdapter(
    private var floors: List<Floor>,
    private val onUpdateClick: (Floor) -> Unit,
    private val onDeleteClick: (Floor) -> Unit
) : RecyclerView.Adapter<FloorAdapter.FloorViewHolder>() {

    class FloorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val floorIdText: TextView = itemView.findViewById(R.id.floorIdText)
        val venueIdText: TextView = itemView.findViewById(R.id.venueIdText)
        val floorNameText: TextView = itemView.findViewById(R.id.textViewFloorValue)
        val btnUpdate: Button = itemView.findViewById(R.id.btnUpdate)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_floor, parent, false)
        return FloorViewHolder(view)
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        val floor = floors[position]
        holder.floorIdText.text = "Floor ID: ${floor.id}"
        holder.venueIdText.text = "Venue ID: ${floor.venueId}"
        holder.floorNameText.text = floor.name

        holder.btnUpdate.setOnClickListener { onUpdateClick(floor) }
        holder.btnDelete.setOnClickListener { onDeleteClick(floor) }
    }

    override fun getItemCount(): Int = floors.size

    fun updateData(newFloors: List<Floor>) {
        floors = newFloors
        notifyDataSetChanged()
    }
}

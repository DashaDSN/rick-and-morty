package com.andersen.rickandmorty.ui.locations.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.ui.locations.Location


class LocationsAdapter(
    private val onCLick: (Location) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.LocationViewHolder>() {

    private var locations = arrayListOf<Location>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
        holder.itemView.setOnClickListener{ onCLick(locations[position]) }
    }

    override fun getItemCount(): Int = locations.size

    fun updateData(data: List<Location>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            LocationsDiffCallback(locations, data)
        )
        locations.clear()
        locations.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    class LocationsDiffCallback(
        private var oldList: List<Location>,
        private var newList: List<Location>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size

        override fun getOldListSize(): Int = oldList.size
    }

    class LocationViewHolder(locationView: View): RecyclerView.ViewHolder(locationView) {
        private val tvName = locationView.findViewById<TextView>(R.id.tvName)
        private val tvType = locationView.findViewById<TextView>(R.id.tvType)
        private val tvDimension = locationView.findViewById<TextView>(R.id.tvDimension)

        fun bind(location: Location) {
            tvName.text = location.name
            tvType.text = location.type
            tvDimension.text = location.dimension
        }
    }
}

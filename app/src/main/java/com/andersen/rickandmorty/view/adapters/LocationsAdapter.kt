package com.andersen.rickandmorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Location


class LocationsAdapter(
    private val onCLick: (Location) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var locations = arrayListOf<Location?>()
        private set
    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_location_item, parent, false)
            LocationViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_progress_bar, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LocationViewHolder) {
            holder.bind(locations[position]!!)
            holder.itemView.setOnClickListener { onCLick(locations[position]!!) }
        }
    }

    override fun getItemCount(): Int = locations.size

    override fun getItemViewType(position: Int): Int {
        return if (locations[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }

    fun getSpanSize(position: Int): Int {
        return if (locations[position] == null) 2 else 1
    }

    fun updateData(data: List<Location?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            LocationsDiffCallback(locations, data)
        )
        locations.clear()
        locations.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addNullItem() {
        isLoading = true
        locations.add(null)
        notifyItemInserted(locations.size - 1)
    }

    fun removeNullItem() {
        if (isLoading) {
            isLoading = false
            if (locations.last() == null) {
                locations.removeLast()
                notifyItemRemoved(locations.size)
            }
        }
    }

    class LocationsDiffCallback(
        private var oldList: List<Location?>,
        private var newList: List<Location?>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]?.id == newList[newItemPosition]?.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size

        override fun getOldListSize(): Int = oldList.size
    }

    class LocationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvType = itemView.findViewById<TextView>(R.id.tvType)
        private val tvDimension = itemView.findViewById<TextView>(R.id.tvDimension)

        fun bind(location: Location) {
            tvName.text = location.name
            tvType.text = location.type
            tvDimension.text = location.dimension
        }
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
    }
}

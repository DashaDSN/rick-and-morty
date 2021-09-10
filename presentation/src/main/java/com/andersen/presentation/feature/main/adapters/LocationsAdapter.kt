package com.andersen.presentation.feature.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.main.Location
import com.andersen.presentation.R
import com.andersen.presentation.feature.base.BaseAdapter

class LocationsAdapter(
    private val onCLick: (Location) -> Unit
) : BaseAdapter<Location>() {

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
            holder.bind(items[position]!!)
            holder.itemView.setOnClickListener { onCLick(items[position]!!) }
        }
    }

    override fun updateData(data: List<Location?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            LocationsDiffCallback(items, data)
        )
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
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
}

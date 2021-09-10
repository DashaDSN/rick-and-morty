package com.andersen.presentation.feature.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.presentation.feature.base.BaseAdapter
import com.bumptech.glide.Glide
import com.andersen.domain.entities.main.Character
import com.andersen.presentation.R

class CharactersAdapter(
    private val onCLick: (Character) -> Unit
) : BaseAdapter<Character>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_character_item, parent, false)
            CharacterViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_progress_bar, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            holder.bind(items[position]!!)
            holder.itemView.setOnClickListener { onCLick(items[position]!!) }
        }
    }

    override fun updateData(data: List<Character?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            CharactersDiffCallback(items, data)
        )
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    class CharactersDiffCallback(
        private var oldList: List<Character?>,
        private var newList: List<Character?>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]?.id == newList[newItemPosition]?.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size

        override fun getOldListSize(): Int = oldList.size
    }

    class CharacterViewHolder(itemView: View): ItemViewHolder<Character>(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvSpecies = itemView.findViewById<TextView>(R.id.tvSpecies)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        private val tvGender = itemView.findViewById<TextView>(R.id.tvGender)
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)

        override fun bind(item: Character) {
            tvName.text = item.name
            tvSpecies.text = item.species
            tvStatus.text = item.status
            tvGender.text = item.gender
            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.ic_character_black_24dp)
                .into(ivImage)
        }
    }
}

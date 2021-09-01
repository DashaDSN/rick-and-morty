package com.andersen.rickandmorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Character
import com.bumptech.glide.Glide

class CharactersAdapter(
    private val onCLick: (Character) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var characters = arrayListOf<Character?>()
        private set
    var isLoading = false

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
            holder.bind(characters[position]!!)
            holder.itemView.setOnClickListener { onCLick(characters[position]!!) }
        }
    }

    override fun getItemCount(): Int = characters.size

    override fun getItemViewType(position: Int): Int {
        return if (characters[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }

    fun getSpanSize(position: Int): Int {
        return if (characters[position] == null) 2 else 1
    }

    fun updateData(data: List<Character?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            CharactersDiffCallback(characters, data)
        )
        characters.clear()
        characters.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addNullItem() {
        isLoading = true
        characters.add(null)
        notifyItemInserted(characters.size - 1)
    }

    fun removeNullItem() {
        if (isLoading) {
            isLoading = false
            if (characters.last() == null) {
                characters.removeLast()
                notifyItemRemoved(characters.size)
            }
        }
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

    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvSpecies = itemView.findViewById<TextView>(R.id.tvSpecies)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        private val tvGender = itemView.findViewById<TextView>(R.id.tvGender)
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)

        fun bind(character: Character) {
            tvName.text = character.name
            tvSpecies.text = character.species
            tvStatus.text = character.status
            tvGender.text = character.gender
            Glide.with(itemView)
                .load(character.image)
                .placeholder(R.drawable.ic_character_black_24dp)
                .into(ivImage)
        }
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
    }
}
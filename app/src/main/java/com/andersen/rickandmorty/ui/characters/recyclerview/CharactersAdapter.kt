package com.andersen.rickandmorty.ui.characters.recyclerview

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
) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var characters = arrayListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
        holder.itemView.setOnClickListener{ onCLick(characters[position]) }
    }

    override fun getItemCount(): Int = characters.size

    fun updateData(data: List<Character>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            CharactersDiffCallback(characters, data)
        )
        characters.clear()
        characters.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    class CharactersDiffCallback(
        private var oldList: List<Character>,
        private var newList: List<Character>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size

        override fun getOldListSize(): Int = oldList.size
    }

    class CharacterViewHolder(characterView: View): RecyclerView.ViewHolder(characterView) {
        private val tvName = characterView.findViewById<TextView>(R.id.tvName)
        private val tvSpecies = characterView.findViewById<TextView>(R.id.tvSpecies)
        private val tvStatus = characterView.findViewById<TextView>(R.id.tvStatus)
        private val tvGender = characterView.findViewById<TextView>(R.id.tvGender)
        private val ivImage = characterView.findViewById<ImageView>(R.id.ivImage)

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

    /*override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val searchText = charSequence.toString()
                val tempList = arrayListOf<Contact>()
                if (searchText.isBlank()) {
                    tempList.addAll(fullContactList)
                } else {
                    for (contact in fullContactList) {
                        if (contact.firstName.contains(searchText, true) || contact.lastName.contains(searchText, true)) {
                            tempList.add(contact)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = tempList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filterResults?.let {
                    updateData(it.values as ArrayList<Contact>)
                }
            }

        }
    }*/
}
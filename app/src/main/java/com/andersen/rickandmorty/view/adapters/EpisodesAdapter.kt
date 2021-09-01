package com.andersen.rickandmorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Episode

class EpisodesAdapter(
    private val onCLick: (Episode) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var episodes = arrayListOf<Episode?>()
        private set
    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_episode_item, parent, false)
            EpisodeViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_progress_bar, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EpisodeViewHolder) {
            holder.bind(episodes[position]!!)
            holder.itemView.setOnClickListener { onCLick(episodes[position]!!) }
        }
    }

    override fun getItemCount(): Int = episodes.size

    override fun getItemViewType(position: Int): Int {
        return if (episodes[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }

    fun getSpanSize(position: Int): Int {
        return if (episodes[position] == null) 2 else 1
    }

    fun updateData(data: List<Episode?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            EpisodesDiffCallback(episodes, data)
        )
        episodes.clear()
        episodes.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addNullItem() {
        isLoading = true
        episodes.add(null)
        notifyItemInserted(episodes.size - 1)
    }

    fun removeNullItem() {
        if (isLoading) {
            isLoading = false
            if (episodes.last() == null) {
                episodes.removeLast()
                notifyItemRemoved(episodes.size)
            }
        }
    }

    class EpisodesDiffCallback(
        private var oldList: List<Episode?>,
        private var newList: List<Episode?>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]?.id == newList[newItemPosition]?.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size

        override fun getOldListSize(): Int = oldList.size
    }

    class EpisodeViewHolder(episodeView: View): RecyclerView.ViewHolder(episodeView) {
        private val tvName = episodeView.findViewById<TextView>(R.id.tvName)
        private val tvEpisode = episodeView.findViewById<TextView>(R.id.tvEpisode)
        private val tvAirDate = episodeView.findViewById<TextView>(R.id.tvAirDate)

        fun bind(episode: Episode) {
            tvName.text = episode.name
            tvEpisode.text = episode.episode
            tvAirDate.text = episode.air_date
        }
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
    }
}

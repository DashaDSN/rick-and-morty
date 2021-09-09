package com.andersen.presentation.feature.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.Episode
import com.andersen.presentation.R
import com.andersen.presentation.feature.base.BaseAdapter

class EpisodesAdapter(
    private val onCLick: (Episode) -> Unit
) : BaseAdapter<Episode>(onCLick) {

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
            holder.bind(items[position]!!)
            holder.itemView.setOnClickListener { onCLick(items[position]!!) }
        }
    }

    override fun updateData(data: List<Episode?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            EpisodesDiffCallback(items, data)
        )
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
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
            tvAirDate.text = episode.airDate
        }
    }
}

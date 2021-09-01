package com.andersen.rickandmorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.view.TextViewWithLabel


class EpisodesAdapter(
    private val onCLick: (Episode) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    private var episodes = arrayListOf<Episode>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_episode_item, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
        holder.itemView.setOnClickListener{ onCLick(episodes[position]) }
    }

    override fun getItemCount(): Int = episodes.size

    fun updateData(data: List<Episode>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            EpisodesDiffCallback(episodes, data)
        )
        episodes.clear()
        episodes.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    class EpisodesDiffCallback(
        private var oldList: List<Episode>,
        private var newList: List<Episode>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
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
}

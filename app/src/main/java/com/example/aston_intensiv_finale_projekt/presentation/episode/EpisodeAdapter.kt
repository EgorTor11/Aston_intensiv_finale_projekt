package com.example.aston_intensiv_finale_projekt.presentation.episode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.databinding.ItemEpisodeBinding
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI


class EpisodeAdapter(
    private val listener: Listener,
) : PagingDataAdapter<EpisodeResultUI, EpisodeAdapter.EpisodeHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(inflater, parent, false)
        return EpisodeHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        val resultEpisode = getItem(position)
        with(holder.binding) {
            tvNameEpisode.text = resultEpisode?.name
            tvEpisodeCode.text = resultEpisode?.episode
            tvAirDate.text = resultEpisode?.air_date
            root.setOnClickListener {
                listener.onRootClick(resultEpisode as EpisodeResultUI)
            }
        }
    }

    interface Listener {
        fun onRootClick(episodeResult: EpisodeResultUI)
    }

    class EpisodeHolder(
        val binding: ItemEpisodeBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<EpisodeResultUI>() {
        override fun areItemsTheSame(oldItem: EpisodeResultUI, newItem: EpisodeResultUI): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: EpisodeResultUI,
            newItem: EpisodeResultUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}

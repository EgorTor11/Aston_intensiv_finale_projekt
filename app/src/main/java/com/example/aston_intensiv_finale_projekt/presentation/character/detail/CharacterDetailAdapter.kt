package com.example.aston_intensiv_finale_projekt.presentation.character.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.databinding.ItemEpisodeBinding
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI

class CharacterDetailAdapter(
    private val listener: Listener,
) : ListAdapter<EpisodeResultUI, CharacterDetailAdapter.CharacterDetailHolder>(ItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDetailHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(inflater, parent, false)
        return CharacterDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterDetailHolder, position: Int) {
        val resultEpisode = getItem(position)
        with(holder.binding) {
            tvNameEpisode.text = resultEpisode.name
            tvAirDate.text = resultEpisode.air_date
            tvEpisodeCode.text = resultEpisode.episode
            root.setOnClickListener {
                listener.onRootClick(resultEpisode)
            }
        }
    }

    interface Listener {
        fun onRootClick(episodeResult: EpisodeResultUI)
    }

    class CharacterDetailHolder(
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

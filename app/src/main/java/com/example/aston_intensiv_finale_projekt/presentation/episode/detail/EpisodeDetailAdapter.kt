package com.example.aston_intensiv_finale_projekt.presentation.episode.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.databinding.ItemCharacterBinding
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI

class EpisodeDetailAdapter(
    private val listener: Listener,
) : ListAdapter<CharacterResultUI, EpisodeDetailAdapter.EpisodeDetailHolder>(ItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return EpisodeDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeDetailHolder, position: Int) {
        val resultCharacter = getItem(position)
        with(holder.binding) {
            root.tag = resultCharacter
            tvName.text = resultCharacter.name
            tvGender.text = resultCharacter.gender
            tvStatus.text = resultCharacter.status
            tvSpecies.text = resultCharacter.species
            Glide.with(root.context).load(resultCharacter.image).into(imageAvatar)
            root.setOnClickListener {
                listener.onRootClick(resultCharacter)
            }
        }
    }

    interface Listener {
        fun onRootClick(characterResult: CharacterResultUI)
    }

    class EpisodeDetailHolder(
        val binding: ItemCharacterBinding,
    ) : RecyclerView.ViewHolder(binding.root)


    object ItemCallback : DiffUtil.ItemCallback<CharacterResultUI>() {

        override fun areItemsTheSame(
            oldItem: CharacterResultUI,
            newItem: CharacterResultUI
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CharacterResultUI,
            newItem: CharacterResultUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}

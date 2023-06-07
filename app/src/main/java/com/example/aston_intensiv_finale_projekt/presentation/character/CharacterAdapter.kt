package com.example.aston_intensiv_finale_projekt.presentation.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.databinding.ItemCharacterBinding
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI


class CharacterAdapter(
    private val listener: Listener,
) : PagingDataAdapter<CharacterResultUI, CharacterAdapter.CharacterHolder>(ItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val resultCharacter = getItem(position)
        with(holder.binding) {
            tvName.text = resultCharacter?.name
            Glide.with(root.context).load(resultCharacter?.image).into(imageAvatar)
            tvSpecies.text = resultCharacter?.species
            tvStatus.text = resultCharacter?.status
            tvGender.text = resultCharacter?.gender
            root.setOnClickListener {
                listener.onRootClick(resultCharacter as CharacterResultUI)
            }
        }
    }

    interface Listener {
        fun onRootClick(characterResult: CharacterResultUI)
    }

    class CharacterHolder(
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

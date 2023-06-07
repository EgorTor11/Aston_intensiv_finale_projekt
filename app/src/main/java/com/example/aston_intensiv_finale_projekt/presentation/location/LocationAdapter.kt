package com.example.aston_intensiv_finale_projekt.presentation.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.databinding.ItemEpisodeBinding
import com.example.aston_intensiv_finale_projekt.databinding.ItemLocationBinding
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationResultUI


class LocationAdapter(
    private val listener: Listener,
) : PagingDataAdapter<LocationResultUI, LocationAdapter.LocationHolder>(ItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater, parent, false)
        return LocationHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val resultLocation = getItem(position)
        with(holder.binding) {
            root.tag = resultLocation
            tvNameLocation.text = resultLocation?.name
            tvDimension.text = resultLocation?.dimension
            tvType.text = resultLocation?.type
            root.setOnClickListener {
                listener.onRootClick(resultLocation as LocationResultUI)
            }
        }
    }

    interface Listener {
        fun onRootClick(locationResult: LocationResultUI)
    }

    class LocationHolder(
        val binding: ItemLocationBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<LocationResultUI>() {
        override fun areItemsTheSame(
            oldItem: LocationResultUI,
            newItem: LocationResultUI
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: LocationResultUI,
            newItem: LocationResultUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}

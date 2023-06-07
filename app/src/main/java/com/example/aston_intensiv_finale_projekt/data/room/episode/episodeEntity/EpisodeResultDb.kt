package com.example.aston_intensiv_finale_projekt.data.room.episode.episodeEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_episode")
data class EpisodeResultDb(
    val air_date: String,
    val episode: String,
    @PrimaryKey()
    val id: Int,
    val name: String,
)
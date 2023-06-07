package com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models

data class EpisodeResult(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)
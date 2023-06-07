package com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel


data class CharacterResultDetail(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: CharacterLocationDetail,
    val name: String,
    val origin: CharacterOriginDetail,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

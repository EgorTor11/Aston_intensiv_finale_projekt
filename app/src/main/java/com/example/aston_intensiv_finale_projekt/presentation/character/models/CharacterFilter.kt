package com.example.aston_intensiv_finale_projekt.presentation.character.models

data class CharacterFilter(
    val name: String,
    val species: String = "",
    val status: String = "",
    val gender: String = ""
)
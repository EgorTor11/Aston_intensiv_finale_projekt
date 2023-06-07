package com.example.aston_intensiv_finale_projekt.presentation.location.detail.detailModel

data class LocationResultDetail(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

package com.example.aston_intensiv_finale_projekt.data.room.location.locationEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_location")
data class LocationResultDb(
    val created: String,
    val dimension: String,
    @PrimaryKey()
    val id: Int,
    val name: String,
    //val residents: List<String>,
    val type: String,
    val url: String
)

package com.example.aston_intensiv_finale_projekt.data.retrofit.location

import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.Location
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.LocationResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {
    @GET("location")
    suspend fun getLocation(
        @Query("page") pageNumber: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String,
    ): Location

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: String): Response<LocationResult>

    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String): Response<List<CharacterResult>>
}
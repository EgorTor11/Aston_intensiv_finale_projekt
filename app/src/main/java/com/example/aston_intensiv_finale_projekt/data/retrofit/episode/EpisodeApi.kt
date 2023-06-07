package com.example.aston_intensiv_finale_projekt.data.retrofit.episode

import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.Episode
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {
    @GET("episode")
    suspend fun getEpisode(
        @Query("page") pageNumber: Int,
        @Query("name") name: String,
        @Query("episode") episode: String,
    ): Episode
    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: String): Response<EpisodeResult>

    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String): Response<List<CharacterResult>>
}
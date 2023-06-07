package com.example.aston_intensiv_finale_projekt.data.retrofit.character
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.Character
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.Episode
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun getCharacter(@Query("page") pageNumber: Int,
                             @Query("name") name: String,
                             @Query("status") status: String,
                             @Query("species") species: String,
                             @Query("gender") gender: String,): Character
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Response<CharacterResult>

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids") ids: String): Response<List<EpisodeResult>>

}
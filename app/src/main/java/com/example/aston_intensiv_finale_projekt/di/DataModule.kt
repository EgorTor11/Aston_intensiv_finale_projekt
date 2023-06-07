package com.example.aston_intensiv_finale_projekt.di

import android.content.Context
import com.example.aston_intensiv_finale_projekt.data.CharacterRepository
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.data.LocationRepository
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.CharacterApi
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.EpisodeApi
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.LocationApi
import com.example.aston_intensiv_finale_projekt.data.room.character.CharacterDao
import com.example.aston_intensiv_finale_projekt.data.room.MainDatabase
import com.example.aston_intensiv_finale_projekt.data.room.episode.EpisodeDao
import com.example.aston_intensiv_finale_projekt.data.room.location.LocationDao
import com.example.aston_intensiv_finale_projekt.domain.character.CharacterRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.location.LocationRepositoryInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class DataModule() {
    @Provides
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCharacterApi(retrofit: Retrofit) =
        retrofit.create(CharacterApi::class.java)

    @Provides
    fun provideEpisodeApi(retrofit: Retrofit) =
        retrofit.create(EpisodeApi::class.java)

    @Provides
    fun provideLocationApi(retrofit: Retrofit) =
        retrofit.create(LocationApi::class.java)

    @Provides
    fun provideCharacterDao(context: Context): CharacterDao {
        return MainDatabase.getMainDatabase(context).getCharacterDao()
    }

    @Provides
    fun provideEpisodeDao(context: Context): EpisodeDao {
        return MainDatabase.getMainDatabase(context).getEpisodeDao()
    }

    @Provides
    fun provideLocationDao(context: Context): LocationDao {
        return MainDatabase.getMainDatabase(context).getLocationDao()
    }

    @Provides
    fun provideCharacterRepository(
        characterApi: CharacterApi, characterDao: CharacterDao, context: Context
    ): CharacterRepositoryInterface {
        return CharacterRepository(characterApi, characterDao, context = context)
    }

    @Provides
    fun provideEpisodeRepository(
        episodeApi: EpisodeApi, episodeDao: EpisodeDao, context: Context
    ): EpisodeRepositoryInterface {
        return EpisodeRepository(episodeApi, episodeDao, context = context)
    }

    @Provides
    fun provideLocationRepository(
        locationApi: LocationApi, locationDao: LocationDao, context: Context
    ): LocationRepositoryInterface {
        return LocationRepository(locationApi, locationDao, context = context)
    }
}
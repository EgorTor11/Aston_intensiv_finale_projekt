package com.example.aston_intensiv_finale_projekt.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aston_intensiv_finale_projekt.data.room.character.CharacterDao
import com.example.aston_intensiv_finale_projekt.data.room.character.characterEntity.CharacterResultDb
import com.example.aston_intensiv_finale_projekt.data.room.episode.EpisodeDao
import com.example.aston_intensiv_finale_projekt.data.room.episode.episodeEntity.EpisodeResultDb
import com.example.aston_intensiv_finale_projekt.data.room.location.LocationDao
import com.example.aston_intensiv_finale_projekt.data.room.location.locationEntity.LocationResultDb


@Database(entities = [CharacterResultDb::class,EpisodeResultDb::class,LocationResultDb::class], version = 7)
abstract class MainDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getLocationDao(): LocationDao
    companion object{
        private var database: MainDatabase? = null
        private val ANY = Any()

        fun getMainDatabase(context: Context): MainDatabase{
        synchronized(ANY){
            database?.let {
                return it
            }
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java,
                "characterDb"
            ).build()
                database = instance
                return instance
            }
        }
    }
}
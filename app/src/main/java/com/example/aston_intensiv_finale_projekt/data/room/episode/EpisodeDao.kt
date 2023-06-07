package com.example.aston_intensiv_finale_projekt.data.room.episode

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.aston_intensiv_finale_projekt.data.room.character.characterEntity.CharacterResultDb
import com.example.aston_intensiv_finale_projekt.data.room.episode.episodeEntity.EpisodeResultDb


@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(list: List<EpisodeResultDb>)

    @Query("DELETE FROM item_episode")
    suspend fun deleteAllEpisodes()


    @Transaction
    suspend fun refreshEpisodes(list: List<EpisodeResultDb>) {
        deleteAllEpisodes()
        insertEpisodes(list)
    }


    @Query(
        "SELECT * FROM item_episode WHERE " +
                "(:name = '' OR name LIKE '%' || :name || '%') AND " +
                "(:episode = '' OR LOWER(episode) LIKE '%' || LOWER(:episode) || '%')"
    )
    fun getPagingSource(
        name: String,
        episode: String,
    ): PagingSource<Int, EpisodeResultDb>


}
package com.example.aston_intensiv_finale_projekt.data.room.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.aston_intensiv_finale_projekt.data.room.character.characterEntity.CharacterResultDb


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(list: List<CharacterResultDb>)

    @Query("DELETE FROM item_character")
    suspend fun deleteAllCharacters()

    @Transaction
    suspend fun refreshCharacters(list: List<CharacterResultDb>) {
        deleteAllCharacters()
        insertCharacters(list)
    }


    @Query(
        "SELECT * FROM item_character WHERE " +
                "(:name = '' OR name LIKE '%' || :name || '%') AND " +
                "(:status = '' OR LOWER(status) = LOWER(:status)) AND " +
                "(:species = '' OR LOWER(species)  LIKE '%' || LOWER(:species) || '%') AND " +
                "(:gender = '' OR LOWER(gender) = LOWER(:gender))"
    )
    fun getPagingSource(
        name: String,
        status: String,
        species: String,
        gender: String,
    ): PagingSource<Int, CharacterResultDb>


}
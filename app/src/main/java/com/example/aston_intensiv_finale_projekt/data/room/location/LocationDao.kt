package com.example.aston_intensiv_finale_projekt.data.room.location

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.aston_intensiv_finale_projekt.data.room.location.locationEntity.LocationResultDb

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(list: List<LocationResultDb>)

    @Query("DELETE FROM item_location")
    suspend fun deleteAllLocations()


    @Transaction
    suspend fun refreshLocations(list: List<LocationResultDb>) {
        deleteAllLocations()
        insertLocations(list)
    }


    @Query(
        "SELECT * FROM item_location WHERE " +
                "(:name = '' OR name LIKE '%' || :name || '%') AND " +
                "(:type = '' OR LOWER(type) LIKE '%' || LOWER(:type) || '%') AND"+
                "(:dimension = '' OR LOWER(dimension) LIKE '%' || LOWER(:dimension) || '%')"
    )
    fun getPagingSource(
        name: String,
        type: String,
        dimension:String
    ): PagingSource<Int, LocationResultDb>


}
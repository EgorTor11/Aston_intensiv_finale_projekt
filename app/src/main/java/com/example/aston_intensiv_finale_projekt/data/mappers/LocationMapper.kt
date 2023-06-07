package com.example.aston_intensiv_finale_projekt.data.mappers

import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.LocationResult
import com.example.aston_intensiv_finale_projekt.data.room.location.locationEntity.LocationResultDb
import com.example.aston_intensiv_finale_projekt.presentation.location.detail.detailModel.LocationResultDetail
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationResultUI

class LocationMapper {
    fun mapLocationResultForLocationResultDb(locationResult: LocationResult): LocationResultDb {
        return LocationResultDb(
            id = locationResult.id,
            name = locationResult.name,
            type = locationResult.type,
            dimension = locationResult.dimension,
            created=locationResult.created,
            url = locationResult.url,

        )
    }

    fun mapLocationResultDbForLocationResultUI(locationResult: LocationResultDb): LocationResultUI {
        return LocationResultUI(
            id = locationResult.id,
            name = locationResult.name,
            type = locationResult.type,
            dimension = locationResult.dimension,
            created=locationResult.created,
            url = locationResult.url,
        )
    }
    fun mapLocationResultForLocationResultDetail(locationResult: LocationResult): LocationResultDetail {
        return LocationResultDetail(
            id = locationResult.id,
            name = locationResult.name,
            type = locationResult.type,
            dimension = locationResult.dimension,
            created=locationResult.created,
            url = locationResult.url,
            residents = locationResult.residents
        )
    }
}
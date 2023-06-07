package com.example.aston_intensiv_finale_projekt.domain.location.listUseCases

import androidx.paging.PagingData
import com.example.aston_intensiv_finale_projekt.domain.location.LocationRepositoryInterface
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationResultUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingListLocationUseCase@Inject constructor(val locationRepository: LocationRepositoryInterface) {
    suspend fun execute(name: String, type: String,dimension:String): Flow<PagingData<LocationResultUI>> {
        return  locationRepository.getLocationsFromApi(name,type,dimension)
    }
}
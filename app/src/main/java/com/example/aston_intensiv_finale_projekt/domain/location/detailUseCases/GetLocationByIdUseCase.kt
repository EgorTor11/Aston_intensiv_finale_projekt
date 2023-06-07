package com.example.aston_intensiv_finale_projekt.domain.location.detailUseCases

import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.LocationResult
import com.example.aston_intensiv_finale_projekt.domain.location.LocationRepositoryInterface
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(val locationRepository: LocationRepositoryInterface) {
    suspend fun execute(id: Int): LocationResult {
        return  locationRepository.getLocationById(id)
    }
}
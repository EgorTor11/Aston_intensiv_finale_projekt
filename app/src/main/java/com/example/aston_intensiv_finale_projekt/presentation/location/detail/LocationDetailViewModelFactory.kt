package com.example.aston_intensiv_finale_projekt.presentation.location.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_finale_projekt.data.LocationRepository
import com.example.aston_intensiv_finale_projekt.domain.location.detailUseCases.GetCharactersListByIdsUseCase
import com.example.aston_intensiv_finale_projekt.domain.location.detailUseCases.GetLocationByIdUseCase
import javax.inject.Inject

class LocationDetailViewModelFactory @Inject constructor(
    val getLocationByIdUseCase: GetLocationByIdUseCase,
    val getCharactersListByIdsUseCase: GetCharactersListByIdsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationDetailViewModel(getLocationByIdUseCase, getCharactersListByIdsUseCase) as T
    }
}
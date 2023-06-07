package com.example.aston_intensiv_finale_projekt.presentation.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_finale_projekt.data.LocationRepository
import com.example.aston_intensiv_finale_projekt.domain.location.listUseCases.GetPagingListLocationUseCase
import javax.inject.Inject

class LocationViewModelFactory @Inject constructor(val getPagingListLocationUseCase: GetPagingListLocationUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel(getPagingListLocationUseCase) as T
    }
}
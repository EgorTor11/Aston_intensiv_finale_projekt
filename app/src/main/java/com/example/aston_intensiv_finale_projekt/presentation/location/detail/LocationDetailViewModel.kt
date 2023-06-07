package com.example.aston_intensiv_finale_projekt.presentation.location.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.data.LocationRepository
import com.example.aston_intensiv_finale_projekt.data.mappers.CharacterMapper
import com.example.aston_intensiv_finale_projekt.data.mappers.LocationMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.LocationResult
import com.example.aston_intensiv_finale_projekt.domain.location.detailUseCases.GetCharactersListByIdsUseCase
import com.example.aston_intensiv_finale_projekt.domain.location.detailUseCases.GetLocationByIdUseCase
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import com.example.aston_intensiv_finale_projekt.presentation.location.detail.detailModel.LocationResultDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    val getLocationByIdUseCase: GetLocationByIdUseCase,
    val getCharactersListByIdsUseCase: GetCharactersListByIdsUseCase
) : ViewModel() {
    val locationResultLiveData = MutableLiveData<LocationResultDetail>()
    val characterResultListLiveData = MutableLiveData<List<CharacterResultUI>>()
    fun getLocationById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            locationResultLiveData.postValue(
                LocationMapper().mapLocationResultForLocationResultDetail(
                    getLocationByIdUseCase.execute(id)
                )
            )
        }
    }

    fun getCharactersListByIds(ids: String) {
        viewModelScope.launch(Dispatchers.IO) {
            characterResultListLiveData.postValue(
                getCharactersListByIdsUseCase.execute(ids)
                    .map { CharacterMapper().mapCharacterResultForCharacterResultUI(it) })
        }
    }
}
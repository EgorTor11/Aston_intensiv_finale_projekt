package com.example.aston_intensiv_finale_projekt.presentation.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.data.LocationRepository
import com.example.aston_intensiv_finale_projekt.domain.location.listUseCases.GetPagingListLocationUseCase
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeFilter
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest



class LocationViewModel(val getPagingListLocationUseCase: GetPagingListLocationUseCase) : ViewModel() {
    private val searchByFilter = MutableLiveData(LocationFilter(""))

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    suspend fun getFullListLocations() = searchByFilter.asFlow()
        .debounce(500)
        .flatMapLatest {
            getPagingListLocationUseCase.execute(it.name, it.type,it.dimension)
        }
        .cachedIn(viewModelScope)

    fun setSearchByFilter(locationFilter: LocationFilter) {
        searchByFilter.value = locationFilter
    }

//    var characterFlow: Flow<PagingData<CharacterResultUI>> =
//        characterRepository.getCharactersFromApi().cachedIn(viewModelScope)
//    // .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}


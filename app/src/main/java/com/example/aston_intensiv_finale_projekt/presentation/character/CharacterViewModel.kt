package com.example.aston_intensiv_finale_projekt.presentation.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.aston_intensiv_finale_projekt.domain.character.listUseCases.GetPagingListCharactersUseCase
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest


class CharacterViewModel(val getPagingListCharactersUseCase: GetPagingListCharactersUseCase) : ViewModel() {
    private val searchByFilter = MutableLiveData(CharacterFilter(""))

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    suspend fun getFullListCharacter() = searchByFilter.asFlow()
        .debounce(500)
        .flatMapLatest {
            getPagingListCharactersUseCase.execute(it.name, it.status, it.species, it.gender)
        }
        .cachedIn(viewModelScope)

    fun setSearchByFilter(characterFilter: CharacterFilter) {
        searchByFilter.value = characterFilter
    }
}


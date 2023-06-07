package com.example.aston_intensiv_finale_projekt.presentation.character.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.aston_intensiv_finale_projekt.data.mappers.CharacterMapper
import com.example.aston_intensiv_finale_projekt.data.mappers.EpisodeMapper
import com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases.GetCharacterByIdUseCase
import com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases.GetEpisodesListByIdsUseCase
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterResultDetail
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    val getEpisodesListByIdsUseCase: GetEpisodesListByIdsUseCase
) : ViewModel() {
    val characterResultLiveData = MutableLiveData<CharacterResultDetail>()
    val episodeResultListLiveData = MutableLiveData<List<EpisodeResultUI>>()
    fun getCharacterById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterResultLiveData.postValue(CharacterMapper().mapCharacterResultForCharacterResultDetail(getCharacterByIdUseCase.execute(id)))
        }
    }

    fun getEpisodesListByIds(ids: String) {
        viewModelScope.launch(Dispatchers.IO) {
            episodeResultListLiveData.postValue(
                getEpisodesListByIdsUseCase.execute(ids)
                    .map { EpisodeMapper().mapEpisodeResultForEpisodeResultUI(it) })
        }
    }
}
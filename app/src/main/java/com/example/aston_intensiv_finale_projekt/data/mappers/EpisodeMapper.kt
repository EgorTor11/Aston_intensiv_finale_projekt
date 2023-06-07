package com.example.aston_intensiv_finale_projekt.data.mappers


import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterLocation
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterOrigin
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.data.room.character.characterEntity.CharacterResultDb
import com.example.aston_intensiv_finale_projekt.data.room.episode.episodeEntity.EpisodeResultDb
import com.example.aston_intensiv_finale_projekt.presentation.episode.detail.detailModel.EpisodeResultDetail
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import javax.inject.Inject

class EpisodeMapper @Inject constructor() {

    fun mapEpisodeResultForEpisodeResultDb(episodeResult: EpisodeResult): EpisodeResultDb {
        return EpisodeResultDb(
            id = episodeResult.id,
            name = episodeResult.name,
            air_date = episodeResult.air_date,
            episode = episodeResult.episode
        )
    }

    fun mapCharacterResultDbForCharacterResult(characterResultDb: CharacterResultDb): CharacterResult {
        return CharacterResult(
            created = characterResultDb.created,
            id = characterResultDb.id,
            status = characterResultDb.status,
            name = characterResultDb.name,
            species = characterResultDb.species,
            image = characterResultDb.image,
            location = CharacterLocation(characterResultDb.location, ""),
            url = characterResultDb.url,
            type = characterResultDb.type,
            episode = emptyList(),
            gender = characterResultDb.gender,
            origin = CharacterOrigin(characterResultDb.origin, "")
        )
    }

    fun mapEpisodeResultDbForEpisodeResultUI(episodeResultDb: EpisodeResultDb): EpisodeResultUI {
        return EpisodeResultUI(
            id = episodeResultDb.id,
            name = episodeResultDb.name,
            episode = episodeResultDb.episode,
            air_date = episodeResultDb.air_date
        )
    }

    fun mapEpisodeResultForEpisodeResultUI(episodeResult: EpisodeResult): EpisodeResultUI {
        return EpisodeResultUI(
            id = episodeResult.id,
            name = episodeResult.name,
            episode = episodeResult.episode,
            air_date = episodeResult.air_date
        )
    }

    fun mapEpisodeResultForEpisodeResultDetail(episodeResult: EpisodeResult): EpisodeResultDetail {
        return EpisodeResultDetail(
            id = episodeResult.id,
            name = episodeResult.name,
            episode = episodeResult.episode,
            air_date = episodeResult.air_date,
            characters = episodeResult.characters,
            created = episodeResult.created,
            url = episodeResult.url
        )
    }
}
package com.example.aston_intensiv_finale_projekt.data.mappers


import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterLocation
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterOrigin
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.room.character.characterEntity.CharacterResultDb
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterLocationDetail
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterOriginDetail
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterResultDetail
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import javax.inject.Inject

class CharacterMapper @Inject constructor() {

    fun mapCharacterResultForCharacterResultDb(characterResult: CharacterResult): CharacterResultDb {
        return CharacterResultDb(
            id = characterResult.id,
            name = characterResult.name,
            gender = characterResult.gender,
            status = characterResult.status,
            species = characterResult.species,
            image = characterResult.image,
            url = characterResult.url,
            type = characterResult.type,
            created = characterResult.created,
            location = characterResult.location.name.toString(),
            origin = characterResult.origin.name.toString()

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
            location = CharacterLocation(characterResultDb.location,""),
            url = characterResultDb.url,
            type = characterResultDb.type,
            episode = emptyList(),
            gender = characterResultDb.gender,
            origin = CharacterOrigin(characterResultDb.origin,"")
        )
    }

    fun mapCharacterResultDbForCharacterResultUI(characterResultDb: CharacterResultDb): CharacterResultUI {
        return CharacterResultUI(
            id = characterResultDb.id,
            status = characterResultDb.status,
            name = characterResultDb.name,
            species = characterResultDb.species,
            image = characterResultDb.image,
            gender = characterResultDb.gender,
        )
    }
    fun mapCharacterResultForCharacterResultUI(characterResult: CharacterResult): CharacterResultUI {
        return CharacterResultUI(
            id = characterResult.id,
            status = characterResult.status,
            name = characterResult.name,
            species = characterResult.species,
            image = characterResult.image,
            gender = characterResult.gender,
        )
    }

    fun mapCharacterResultForCharacterResultDetail(characterResult: CharacterResult): CharacterResultDetail {
        val characterLocationDetail=CharacterLocationDetail(characterResult.location.name,characterResult.location.url)
        val characterOriginDetail=
            CharacterOriginDetail(characterResult.origin.name,characterResult.origin.url)
        return CharacterResultDetail(
            id = characterResult.id,
            status = characterResult.status,
            name = characterResult.name,
            species = characterResult.species,
            image = characterResult.image,
            gender = characterResult.gender,
            location = characterLocationDetail,
            origin = characterOriginDetail,
            created = characterResult.created,
            episode = characterResult.episode,
            type = characterResult.type,
            url = characterResult.url
        )
    }
}
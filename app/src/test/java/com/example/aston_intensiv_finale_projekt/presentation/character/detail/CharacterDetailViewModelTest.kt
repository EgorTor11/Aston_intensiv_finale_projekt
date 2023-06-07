package com.example.aston_intensiv_finale_projekt.presentation.character.detail

import com.example.aston_intensiv_finale_projekt.data.CharacterRepository
import com.example.aston_intensiv_finale_projekt.data.mappers.CharacterMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterLocation
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterOrigin
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases.GetCharacterByIdUseCase
import com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases.GetEpisodesListByIdsUseCase
import com.example.aston_intensiv_finale_projekt.kotestApi.CoroutinesKotestExtension
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterLocationDetail
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterOriginDetail
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.detailModel.CharacterResultDetail
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence

class CharacterDetailViewModelTest : BehaviorSpec({
    extension(CoroutinesKotestExtension())
    val getCharacterByIdUseCase = mockk<GetCharacterByIdUseCase>()
    val getEpisodesListByIdsUseCase = mockk<GetEpisodesListByIdsUseCase>()
    val viewModel = CharacterDetailViewModel(getCharacterByIdUseCase, getEpisodesListByIdsUseCase)
    Given("check useCase request") {
        var testId=1
        val expectedResponse = CharacterResultDetail(
            "",
            CharacterRepository.defEpisodeList,
            "",
            testId,
            "",
            CharacterLocationDetail("", ""),
            "",
            CharacterOriginDetail("", ""),
            "",
            "",
            "",
            ""
        )

        When("getCharacterByIdUseCase.execute(testId)") {
            coEvery {CharacterMapper().mapCharacterResultForCharacterResultDetail( getCharacterByIdUseCase.execute(testId)) } returns expectedResponse
          viewModel.getCharacterById(testId)
            Then("should call characterResultLiveData.value") {
                viewModel.characterResultLiveData.value shouldBe expectedResponse
            }
        }
    }

})

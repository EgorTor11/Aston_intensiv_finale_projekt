package com.example.aston_intensiv_finale_projekt.kotestApi

import io.kotest.core.annotation.AutoScan
import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@AutoScan
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesKotestExtension : Extension, BeforeSpecListener, AfterSpecListener {

    private val testDispatcher = UnconfinedTestDispatcher()

    override suspend fun beforeSpec(spec: Spec) {
        Dispatchers.setMain(testDispatcher)
    }

    override suspend fun afterSpec(spec: Spec) {
        Dispatchers.resetMain()
    }
}
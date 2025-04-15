package com.example.fish_interactors

import com.example.core.DataState
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.fish_datasource.cache.FishCache
import com.example.fish_domain.Fish
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFishFromCache(
    private val cache: FishCache
) {
    fun execute(
        scientificName: String
    ): Flow<DataState<Fish>> = flow {

        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            kotlinx.coroutines.delay(2000)
            val cachedFish = cache.getFish(scientificName)

            if (cachedFish == null) {
                throw Exception("That Fish does not exist in the cache")
            }

            emit(DataState.Data(cachedFish))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Fish>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }
}
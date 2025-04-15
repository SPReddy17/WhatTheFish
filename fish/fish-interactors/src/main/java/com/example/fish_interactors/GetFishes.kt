package com.example.fish_interactors

import com.example.core.DataState
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.fish_datasource.cache.FishCache
import com.example.fish_datasource.network.FishService
import com.example.fish_domain.Fish
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFishes(
    private val service: FishService,
    private val cache :FishCache
) {
    fun execute(): Flow<DataState<List<Fish>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val fishes: List<Fish> = try {
                service.getFishStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Fish>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )
                listOf()
            }

            //cache the network data
            cache.insert(fishes)

            //emit the data from cache
            val cachedFishes = cache.selectAll()

            emit(DataState.Data(cachedFishes))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Fish>>(
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
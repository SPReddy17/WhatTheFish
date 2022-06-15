package com.example.ui_fishdetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.fish_interactors.GetFishFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FishDetailViewModel
@Inject
constructor(
    private val getFishFromCache: GetFishFromCache,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state :MutableState<FishDetailState> = mutableStateOf(FishDetailState())

    init {
        savedStateHandle.get<String>("fishScientificName")?.let { fishScientificName ->
            onTriggerEvent(FishDetailEvents.GetFishFromCache(fishScientificName))
        }
    }
    fun onTriggerEvent(events: FishDetailEvents) {
        when (events) {
            is FishDetailEvents.GetFishFromCache -> {
                getFishFromCache(events.scientificName)
            }
        }
    }

    private fun getFishFromCache(scientificName: String) {
        getFishFromCache.execute(scientificName = scientificName).onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState )
                }
                is DataState.Data -> {
                    state.value = state.value.copy(fish = dataState.data)
                }
                is DataState.Response -> {
                    // TODO
                }
            }
        }.launchIn(viewModelScope)
    }
}
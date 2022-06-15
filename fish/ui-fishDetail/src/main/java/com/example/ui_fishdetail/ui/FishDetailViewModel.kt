package com.example.ui_fishdetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.Queue
import com.example.core.UIComponent
import com.example.fish_interactors.GetFishFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FishDetailViewModel
@Inject
constructor(
    private val getFishFromCache: GetFishFromCache,
    private val savedStateHandle: SavedStateHandle,
    @Named("fishListLogger") private val logger: Logger,
) : ViewModel() {

    val state: MutableState<FishDetailState> = mutableStateOf(FishDetailState())

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
            is FishDetailEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
        }
    }


    private fun getFishFromCache(scientificName: String) {
        getFishFromCache.execute(scientificName = scientificName).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(fish = dataState.data)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //forces recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //forces recompose
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from dialog queue")
        }
    }
}
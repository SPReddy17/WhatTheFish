package com.example.ui_fishlist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.Queue
import com.example.core.UIComponent
import com.example.fish_domain.FishFilter
import com.example.fish_interactors.FilterFishes
import com.example.fish_interactors.GetFishes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FishListViewModel
@Inject
constructor(
    private val getFishes: GetFishes,
    @Named("fishListLogger") private val logger: Logger,
    private val filterFishes: FilterFishes,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: MutableState<FishListState> = mutableStateOf(FishListState())

    init {
        onTriggerEvent(FishListEvents.GetFishes)
    }

    fun onTriggerEvent(event: FishListEvents) {
        when (event) {
            is FishListEvents.GetFishes -> {
                getFishes()
            }
            is FishListEvents.FilterFishes -> {
                filterFishes()
            }
            is FishListEvents.UpdateFishName -> {
                updateFishName(event.fishName)
            }
            is FishListEvents.UpdateFishFilter -> {
                updateFishFilter(event.fishFilter)
            }
            is FishListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }
            is FishListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
        }
    }

    private fun updateFishFilter(fishFilter: FishFilter) {
        state.value = state.value.copy(fishFilter = fishFilter)
        filterFishes()
    }

    private fun filterFishes() {

        val filteredList = filterFishes.execute(
            current = state.value.fishes,
            fishName = state.value.fishName,
            fishFilter = state.value.fishFilter
        )
        state.value = state.value.copy(filteredFishes = filteredList)
    }

    private fun updateFishName(fishName: String) {
        state.value = state.value.copy(fishName = fishName)
    }

    private fun getFishes() {
        getFishes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(fishes = dataState.data ?: listOf())
                    filterFishes()
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent){
        val queue: Queue<UIComponent> = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //forces recompose
        state.value = state.value.copy(errorQueue =  queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //forces recompose
            state.value = state.value.copy(errorQueue =  queue)
        }catch (e :Exception){
            logger.log("Nothing to remove from dialog queue")
        }
    }
}
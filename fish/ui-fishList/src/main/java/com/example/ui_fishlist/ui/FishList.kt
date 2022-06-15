package com.example.ui_fishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.components.DefaultScreenUI
import com.example.core.ProgressBarState
import com.example.core.UIComponentState
import com.example.ui_fishlist.components.FishListFilter
import com.example.ui_fishlist.components.FishListItem
import com.example.ui_fishlist.components.FishListToolbar
import com.example.ui_fishlist.ui.FishListEvents
import com.example.ui_fishlist.ui.FishListState

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun FishList(
    state: FishListState,
    events: (FishListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (String) -> Unit
) {
    DefaultScreenUI (
        progressBarState = state.progressBarState
            ){
        Column() {
            FishListToolbar(
                fishName = state.fishName,
                onFishNameChanged = { fishName ->
                    events(FishListEvents.UpdateFishName(fishName))
                },
                onExecuteSearch = {
                    events(FishListEvents.FilterFishes)
                },
                onShowFilterDialog = {
                    events(FishListEvents.UpdateFilterDialogState(UIComponentState.Show))
                }
            )
            LazyColumn {
                items(state.filteredFishes) { fish ->

                    FishListItem(
                        fish = fish,
                        onSelectFish = { fishScientificName ->
                            navigateToDetailScreen(fishScientificName)
                        },
                        imageLoader = imageLoader
                    )
                }
            }
        }

        if (state.filterDialogState is UIComponentState.Show) {
            FishListFilter(
                fishFilter =
                state.fishFilter,
                onUpdateFishFilter = { fishFilter ->
                    events(FishListEvents.UpdateFishFilter(fishFilter))
                },
                onCloseDialog = {
                    events(FishListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                }
            )
        }
//
//        if (state.progressBarState is ProgressBarState.Loading) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
    }


}
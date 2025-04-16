package com.example.ui_fishlist.ui
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridCells.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.components.DefaultScreenUI
import com.example.core.UIComponentState
import com.example.ui_fishlist.components.FishListFilter
import com.example.ui_fishlist.components.FishListItem
import com.example.ui_fishlist.components.FishListToolbar
import com.example.ui_fishlist.ui.FishListEvents
import com.example.ui_fishlist.ui.FishListState

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun FishList(
    state: FishListState,
    events: (FishListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (String) -> Unit
) {
    DefaultScreenUI(
        progressBarState = state.progressBarState,
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(FishListEvents.OnRemoveHeadFromQueue)
        }
    ) {
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
            LazyVerticalGrid (
                cells = Fixed(3),
            //     Adaptive(minSize = constraints.maxWidth/2)
                    ){
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
            //
//            @Composable
//            fun PhotoGrid(photos: List<Photo>) {
//                LazyVerticalGrid(
//                    columns = GridCells.Adaptive(minSize = 128.dp)
//                ) {
//                    items(photos) { photo ->
//                        PhotoItem(photo)
//                    }
//                }
//            }
            //
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
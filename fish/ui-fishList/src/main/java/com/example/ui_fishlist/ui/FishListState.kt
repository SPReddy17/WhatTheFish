package com.example.ui_fishlist.ui

import com.example.core.ProgressBarState
import com.example.core.UIComponentState
import com.example.fish_domain.Fish
import com.example.fish_domain.FishFilter

data class FishListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val fishes: List<Fish> = listOf(),
    val filteredFishes: List<Fish> = listOf(),
    val fishName : String  = "",
    val fishFilter : FishFilter = FishFilter.Fish(),
val filterDialogState : UIComponentState = UIComponentState.Hide
)

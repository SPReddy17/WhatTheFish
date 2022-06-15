package com.example.ui_fishdetail.ui

import com.example.core.ProgressBarState
import com.example.fish_domain.Fish

data class FishDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val fish :Fish? = null,
)

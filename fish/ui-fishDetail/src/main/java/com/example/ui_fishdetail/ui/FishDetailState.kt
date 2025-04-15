package com.example.ui_fishdetail.ui

import com.example.core.ProgressBarState
import com.example.core.Queue
import com.example.core.UIComponent
import com.example.fish_domain.Fish

data class FishDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val fish :Fish? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)

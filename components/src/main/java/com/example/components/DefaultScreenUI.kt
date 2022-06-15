package com.example.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import com.example.core.ProgressBarState
import com.example.core.Queue
import com.example.core.UIComponent

@Composable
fun DefaultScreenUI(
    queue: Queue<UIComponent> = Queue(mutableListOf()),
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    onRemoveHeadFromQueue: () -> Unit,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            content()
            if(!queue.isEmpty()){
                queue.peek()?.let { uiComponent ->
                if(uiComponent is UIComponent.Dialog){
                    GenericDialog(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        title = uiComponent.title,
                        description = uiComponent.description,
                        onRemoveHeadFromQueue = onRemoveHeadFromQueue
                    )
                }

                }
            }
            if(progressBarState is ProgressBarState.Loading){
                CircularIndeterminateProgressBar()
            }

        }
    }
}
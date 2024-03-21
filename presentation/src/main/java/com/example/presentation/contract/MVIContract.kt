package com.example.presentation.contract

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MVIContract<ViewState, ViewIntent, SideEffect> {
    fun sendEvent(viewIntent: ViewIntent)
    fun loadingState(): ViewState

    val viewState: StateFlow<ViewState>

    val sideEffect: SharedFlow<SideEffect>

}
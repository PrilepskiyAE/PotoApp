package com.ambrella.fotoApp.ui.state

import androidx.compose.runtime.Immutable
import com.ambrella.fotoApp.utils.State
import com.ambrella.fotoApp.utils.emptyString

interface MainState : State {

    @Immutable
    data class Data(
        val token: String = emptyString(),
        val name: String = emptyString()
    ) : MainState

    object Loading : MainState
}
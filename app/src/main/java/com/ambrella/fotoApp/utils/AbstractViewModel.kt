package com.ambrella.fotoApp.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

interface Action
interface State

abstract class AbstractViewModel : ViewModel() {
    abstract val state: StateFlow<State>
    open fun doAction(action: Action) = Unit
}
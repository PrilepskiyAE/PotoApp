package com.ambrella.fotoApp.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ambrella.fotoApp.domain.usecase.DownloadUseCase
import com.ambrella.fotoApp.domain.usecase.GetFileUseCase
import com.ambrella.fotoApp.ui.state.MainAction
import com.ambrella.fotoApp.ui.state.MainState
import com.ambrella.fotoApp.utils.AbstractViewModel
import com.ambrella.fotoApp.utils.Action
import com.ambrella.fotoApp.utils.prefixHeader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFileUseCase: GetFileUseCase,
    private val downloadUseCase: DownloadUseCase
) :
    AbstractViewModel() {
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Data())
    override val state: StateFlow<MainState> = _state.asStateFlow()
    private val _inputData: MutableStateFlow<MainState.Data> = MutableStateFlow(MainState.Data())
    override fun doAction(action: Action) {
        when (action) {
            is MainAction.OnClickLogin -> {
                viewModelScope.launch {
                    val token = _inputData.value.token
                    val name=_inputData.value.name
                    getFileUseCase.invoke(token
                    .prefixHeader()).onRight {
                        downloadUseCase.invoke(
                            it.href, action.uri,
                            name
                        )
                    }.onLeft {
                        Log.d("TAG999", "doAction: $it")
                    }
                }
            }
            is MainAction.SaveTokenAndNameFile -> {
                _inputData.value=MainState.Data(token = action.token, name = action.nameFile)
                _state.value=_inputData.value
            }
        }
    }
}
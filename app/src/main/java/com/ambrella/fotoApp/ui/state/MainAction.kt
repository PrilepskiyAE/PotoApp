package com.ambrella.fotoApp.ui.state

import com.ambrella.fotoApp.utils.Action


interface MainAction: Action {
    data class OnClickLogin(val token: String) : MainAction
}
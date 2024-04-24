package com.ambrella.fotoApp.ui.state

import android.net.Uri
import com.ambrella.fotoApp.utils.Action


interface MainAction: Action {
    data class OnClickLogin(val uri: Uri) : MainAction
    data class SaveTokenAndNameFile(val token:String, val nameFile:String) : MainAction
}
package com.ambrella.fotoApp.data

import com.ambrella.fotoApp.domain.FileModel
import com.ambrella.fotoApp.utils.emptyString

data class FileResponse(
    val href: String = emptyString(),
    val method: String = emptyString(),
    val templated: Boolean = false,
)

fun FileResponse.asModel()=FileModel(
    href, method, templated)
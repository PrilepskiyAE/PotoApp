package com.ambrella.fotoApp.domain

import com.ambrella.fotoApp.utils.emptyString

data class FileModel (
    val href: String = emptyString(),
    val method: String = emptyString(),
    val templated: Boolean = false,
)
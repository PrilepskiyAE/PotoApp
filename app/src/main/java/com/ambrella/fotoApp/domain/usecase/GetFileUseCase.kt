package com.ambrella.fotoApp.domain.usecase

import arrow.core.Either
import com.ambrella.fotoApp.data.YandexApiRepository
import com.ambrella.fotoApp.domain.FileModel
import com.ambrella.fotoApp.utils.ActionResult
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetFileUseCase@Inject constructor(private val repository: YandexApiRepository){
     suspend fun invoke(token:String): Either<ActionResult<Error>, FileModel> = repository.getFile(token)


}
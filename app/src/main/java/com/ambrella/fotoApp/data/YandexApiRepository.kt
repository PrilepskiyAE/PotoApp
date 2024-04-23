package com.ambrella.fotoApp.data

import arrow.core.Either
import com.ambrella.fotoApp.domain.FileModel
import com.ambrella.fotoApp.utils.ActionResult
import com.ambrella.fotoApp.utils.analyzeResponse
import com.ambrella.fotoApp.utils.makeApiCall
import javax.inject.Inject
import javax.inject.Singleton
interface YandexApiRepository{
    suspend fun getFile(token:String): Either<ActionResult<Error>, FileModel>
    suspend fun getFileList(token:String): Either<ActionResult<Error>, List<FileModel>>
}

class YandexApiRepositoryImpl @Inject constructor(private val data:YandexApi): YandexApiRepository {
     override suspend fun getFile(token:String): Either<ActionResult<Error>, FileModel> {
        val apiData =  makeApiCall {
            analyzeResponse(
                data.getUrlFile(token)
            )
        }
        return when(apiData){
            is ActionResult.Success -> {
                Either.Right(apiData.data.asModel())
            }
            is ActionResult.Error -> {
                Either.Left(ActionResult.Error(apiData.errors))
            }
        }
    }

     override suspend fun getFileList(token:String): Either<ActionResult<Error>, List<FileModel>> {
        return Either.Left(ActionResult.Error(Throwable()))
    }

}
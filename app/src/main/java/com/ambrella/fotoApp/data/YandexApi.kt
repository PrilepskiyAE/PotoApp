package com.ambrella.fotoApp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface YandexApi {
    @GET("download?path=/test/test.jpg")
   suspend fun getUrlFile(@Header("Authorization") token: String): Response<FileResponse>
}
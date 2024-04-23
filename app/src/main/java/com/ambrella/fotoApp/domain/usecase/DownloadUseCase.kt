package com.ambrella.fotoApp.domain.usecase

import android.content.Context
import android.content.ContextWrapper
import android.os.StrictMode
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke(link: String) {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val cw = ContextWrapper(context)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val pictureFile = File(directory, "bImage.jpg")
        URL(link).openStream().use { input ->
            FileOutputStream(pictureFile).use { output ->
                input.copyTo(output)
                Log.e("SAVED_FILE", "storeImage: FILE SAVED")
            }
        }
    }
}
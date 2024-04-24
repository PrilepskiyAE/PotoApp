package com.ambrella.fotoApp.domain.usecase

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.StrictMode
import android.util.Log
import androidx.core.net.toFile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.channels.FileChannel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DownloadUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke(link: String,url:Uri,filename:String) {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val cw = ContextWrapper(context)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val pictureFile = File(directory, filename)


        URL(link).openStream().use { input ->
            FileOutputStream(pictureFile).use { output ->
                input.copyTo(output)

                moveFile(pictureFile,url.toFile() )
                Log.e("SAVED_FILE", "storeImage: FILE SAVED")
                input.close()
                output.close()
            }
        }
    }
}


private fun moveFile(file: File, dir: File) {
    val newFile = File(dir, file.name)
    var outputChannel: FileChannel? = null
    var inputChannel: FileChannel? = null
    try {
        outputChannel = FileOutputStream(newFile).channel
        inputChannel = FileInputStream(file).channel
        inputChannel.transferTo(0, inputChannel.size(), outputChannel)
        inputChannel.close()

    } finally {
        inputChannel?.close()
        outputChannel?.close()
    }
}
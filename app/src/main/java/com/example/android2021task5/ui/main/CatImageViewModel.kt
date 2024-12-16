package com.example.android2021task5.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.android2021task5.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class CatImageViewModel : ViewModel() {

    fun saveImageToFile(imgUrl: String, context: Context) {

        val filename = getFileNameFromUrl(imgUrl)

        viewModelScope.launch {
            val savedFile =  withContext(Dispatchers.IO) {
                return@withContext saveImage(
                    Glide.with(context)
                        .asBitmap()
                        .load(imgUrl)
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .submit()
                        .get(),
                    filename,
                    context
                )
            }

            if (savedFile?.length!! > 0) {
                Toast.makeText(context, context.getString(R.string.image_saved), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, context.getString(R.string.save_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getFileNameFromUrl(imgUrl: String): String {
        return imgUrl.lastIndexOf('/').plus(1).let { it1 ->
            imgUrl.length.let { it2 ->
                imgUrl.substring(
                    it1,
                    it2
                )
            }
        }
    }

    private fun saveImage(image: Bitmap, imageFileName: String, context: Context): String? {
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/" + context.getString(R.string.app_name)
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }

        var savedImagePath: String? = null
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return savedImagePath
    }
}

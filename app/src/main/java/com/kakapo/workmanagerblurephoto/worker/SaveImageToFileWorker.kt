package com.kakapo.workmanagerblurephoto.worker

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kakapo.workmanagerblurephoto.Constants
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SaveImageToFileWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override fun doWork(): Result {
        //Makes notification when the work starts and slows down the work so that
        //it's make easier to see each work request start, even on emulate device
        makeStatusNotification("Saving Image", applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver
        return try{

            val resourceUri = inputData.getString(Constants.KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val imageUrl = MediaStore.Images.Media.insertImage(
                resolver, bitmap, title, dateFormatter.format(Date())
            )

            if (!imageUrl.isNullOrEmpty()){
                val putput = workDataOf(Constants.KEY_IMAGE_URI to imageUrl)

                Result.success()
            }else{
                Timber.e("Writing to media Store Failed")
                Result.failure()
            }
        }catch (e: Exception){
            Timber.e(e)
            Result.failure()
        }
    }
}
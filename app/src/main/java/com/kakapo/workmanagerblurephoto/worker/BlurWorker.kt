package com.kakapo.workmanagerblurephoto.worker

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kakapo.workmanagerblurephoto.Constants
import timber.log.Timber
import java.lang.IllegalArgumentException

class BlurWorker(context: Context, param: WorkerParameters): Worker(context, param) {
    override fun doWork(): Result {
        val appContext = applicationContext

        val resourceUri = inputData.getString(Constants.KEY_IMAGE_URI)
        makeStatusNotification("Blurring Image", appContext)
        sleep()

        return try{
            if (TextUtils.isEmpty(resourceUri)){
                Timber.e("Invalid Input Uri")
                throw IllegalArgumentException("Invalid Input Uri")
            }

            val resolver = appContext.contentResolver
            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val output = blur
        }catch (throwable: Throwable){
            Timber.e(throwable, "Error Aplying blur")
            Result.failure()
        }
    }
}
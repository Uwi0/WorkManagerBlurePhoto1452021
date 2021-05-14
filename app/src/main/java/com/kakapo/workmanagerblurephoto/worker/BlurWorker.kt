package com.kakapo.workmanagerblurephoto.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kakapo.workmanagerblurephoto.Constants

class BlurWorker(context: Context, param: WorkerParameters): Worker(context, param) {
    override fun doWork(): Result {
        val appContext = applicationContext

        val resourceUri = inputData.getString(Constants.KEY_IMAGE_URI)

    }
}
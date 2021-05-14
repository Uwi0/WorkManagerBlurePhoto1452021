package com.kakapo.workmanagerblurephoto.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kakapo.workmanagerblurephoto.Constants
import timber.log.Timber
import java.io.File

class CleanupWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        makeStatusNotification("Cleaning up Temporary Files", applicationContext)
        sleep()

        return try{
            val outputDirectory = File(applicationContext.filesDir, Constants.OUTPUT_PATH)
            if(outputDirectory.exists()){
                val entries = outputDirectory.listFiles()
                if (entries != null){
                    for (entry in entries){
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")){
                            val deleted = entry.delete()
                            Timber.i("Deleted $name - $deleted")
                        }
                    }
                }
            }
            Result.success()
        }catch (exception: Exception){
            Timber.e(exception)
            Result.failure()
        }
    }
}
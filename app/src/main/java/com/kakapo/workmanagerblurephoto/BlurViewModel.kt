package com.kakapo.workmanagerblurephoto

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*

class BlurViewModel(application: Application) : AndroidViewModel(application){
    internal var imageUri: Uri? = null
    internal var outputImageUri: Uri? = null
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfo: LiveData<List<WorkInfo>>

    init{
        outputWorkInfo = workManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT)
    }

    internal fun cancelWork(){
        workManager.cancelUniqueWork(Constants.IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataFromUri(): Data{
        val builder = Data.Builder()
        imageUri?.let{
            builder.putString(Constants.KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    internal fun applyBlur(blurLevel: Int){
        var continuation = workManager
            .beginUniqueWork(
                Constants.IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,

            )
    }
}
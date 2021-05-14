package com.kakapo.workmanagerblurephoto

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager

class BlurViewModel(application: Application) : AndroidViewModel(application){
    internal var imageUri: Uri? = null
    internal var outputImageUri: Uri? = null
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfo: LiveData<List<WorkInfo>>

    init{
        outputWorkInfo = workManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT)
    }
}
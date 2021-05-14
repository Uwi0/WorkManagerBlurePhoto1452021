@file:JvmName("Constants")
package com.kakapo.workmanagerblurephoto

//notifications constants
object Constants {

    //name of notification channel for verbose notifications of background work
    @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notification"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Show Notifications Whenever Work Start"
    @JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    //the name of the image manipulator work
    const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

    //other key
    const val OUTPUT_PATH = "blur_filter_outputs"
    const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    const val TAG_OUTPUT = "OUTPUT"

    const val DELAY_TIME_MILLIS: Long = 3000
}
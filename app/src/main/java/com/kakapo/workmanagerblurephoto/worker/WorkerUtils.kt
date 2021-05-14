@file:JvmName("WorkerUtils")
package com.kakapo.workmanagerblurephoto.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kakapo.workmanagerblurephoto.Constants
import com.kakapo.workmanagerblurephoto.R
import timber.log.Timber

fun makeStatusNotification(message: String, context: Context){

    //make channel if necessary
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        //Create the NotificationChannel, but only on API 26+ because
        //the NotificationChannel class is new and not in the support library
        val name = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
        channel.description = description

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    //create notification
    val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(Constants.NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build())
}

fun sleep(){
    try{
        Thread.sleep(Constants.DELAY_TIME_MILLIS, 0)
    }catch (e: InterruptedException){
        Timber.e(e.message)
    }
}

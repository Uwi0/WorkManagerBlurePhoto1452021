@file:JvmName("WorkerUtils")
package com.kakapo.workmanagerblurephoto.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.renderscript.*
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kakapo.workmanagerblurephoto.Constants
import com.kakapo.workmanagerblurephoto.R
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

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
        Timber.e(e)
    }
}

@WorkerThread
fun blurBitmap(bitmap: Bitmap, applicationContext: Context): Bitmap{
    lateinit var rsContext: RenderScript
    try{
        //create output bitmap
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap.height, bitmap.config
        )

        //Blur the Image
        rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
        val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
        val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
        val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
        theIntrinsic.apply {
            setRadius(10f)
            theIntrinsic.setInput(inAlloc)
            theIntrinsic.forEach(outAlloc)
        }
        outAlloc.copyTo(output)

        return output
    }finally {
        rsContext.finish()
    }
}


@Throws(FileNotFoundException::class)
fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri{
    val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
    val outputDir = File(applicationContext.filesDir, Constants.OUTPUT_PATH)

    if(!outputDir.exists()){
        outputDir.mkdir()
    }

    val outputFile = File(outputDir, name)
    var out: FileOutputStream? = null
    try{
        out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* */, out)
    }finally {
        out?.let {
            try {
                it.close()
            }catch (e: IOException){

            }
        }
    }

    return Uri.fromFile(outputFile)
}
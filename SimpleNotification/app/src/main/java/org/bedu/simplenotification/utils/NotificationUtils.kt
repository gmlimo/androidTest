package org.bedu.simplenotification.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import org.bedu.simplenotification.NotificationApp
import org.bedu.simplenotification.NotificationApp.Companion.CHANNEL_ID
import org.bedu.simplenotification.NotificationApp.Companion.CHANNEL_OTHERS
import org.bedu.simplenotification.NotificationApp.Companion.GRUPO_SIMPLE
import org.bedu.simplenotification.R


@SuppressLint("MissingPermission")
fun simpleNotification(context: Context){

    //lanzamos la notificación
    with(context) {
        val builder = NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.triforce)
            .setColor(getColor(R.color.triforce))
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.simple_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        NotificationManagerCompat.from(this).apply {
            notify(20, builder.build())
        }
    }
}

private fun simpleNotificationBuilder(context: Context, titleId: Int, contentId: Int) =
    NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.triforce)
        .setColor(context.getColor(R.color.triforce))
        .setContentTitle(context.getString(titleId))
        .setContentText(context.getString(contentId))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setGroup(GRUPO_SIMPLE)
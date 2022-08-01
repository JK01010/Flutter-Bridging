package com.example.flutter_bridge

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.media.MediaPlayer
import java.io.IOException
import android.content.res.AssetFileDescriptor



class AlarmReceive : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, DestinationActivity::class.java)
        var player = MediaPlayer()
        i!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
        if(context != null){
        context.startActivity(i);
        val afd: AssetFileDescriptor
        try {
            val  afd = context?.assets?.openFd("azan.mp3")
            afd?.let { player.setDataSource(afd?.fileDescriptor, it.startOffset,afd?.length) }
            afd?.close()
            player.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        player.start()
        }
        // val builder = NotificationCompat.Builder(context!!,"default")
        //     .setSmallIcon(R.drawable.ic_launcher_foreground)
        //     .setContentTitle("Alarm")
        //     .setContentText("Alarm is going off")
        //     .setDefaults(NotificationCompat.DEFAULT_ALL)
        //     .setPriority(NotificationCompat.PRIORITY_HIGH)
        //     .setContentIntent(pendingIntent)
        //     .setAutoCancel(true)

        // val notificationManager = NotificationManagerCompat.from(context)
        // notificationManager.notify( 123, builder.build())
    }
}
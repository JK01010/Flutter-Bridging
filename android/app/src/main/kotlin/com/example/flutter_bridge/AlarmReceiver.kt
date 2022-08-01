package com.example.flutter_bridge

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceive : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, DestinationActivity::class.java)
        i!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
        if(context != null){
        context.startActivity(i);
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
package com.example.flutter_bridge

import android.util.Log
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.widget.TimePicker
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.lang.reflect.Method
import androidx.work.Worker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import java.util.*


class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.flutter_bridge/native_bridge";

    private lateinit var channel: MethodChannel
    private lateinit var calendar : Calendar
    private lateinit var alarmManager : AlarmManager
    private lateinit var pendingIntent : PendingIntent

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        createNotificationChannel()
             channel.setMethodCallHandler { call, result ->
                   if(call.method.equals("showToast")){
        

        } else if(call.method.equals("setTime")){
            val arguments = call.arguments as Map<String, Int>
            var hour = arguments.get("hour") 
            var minute = arguments.get("minute") 
            if(hour == null || minute == null){
                result.error("error", "hour or minute is null", null)
                // return@setMethodCallHandler
            } else {
                  calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = hour 
            calendar[Calendar.MINUTE] =  minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            println("calender time is ${calendar.timeInMillis}")
            println("arguments  is $arguments")
            println("hour  is $hour")
            }
          

            // setAlarm()
            // Toast.makeText(this,"Time set Successfully",Toast.LENGTH_LONG).show()
        } else if(call.method.equals("setAlarm")){
           setAlarm()
        }
        }
    }
      
 
        private fun setAlarm(){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent( this,AlarmReceive::class.java)
        pendingIntent = PendingIntent.getBroadcast( this, 0,intent, 0)
        println("calender time is ${calendar.timeInMillis}")
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
        Toast.makeText(this,"Alarm set Successfully",Toast.LENGTH_LONG).show()

    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "Alarm"
            val description = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("default", name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
      fun setOneTimeWorkRequest(){
      val uploadRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
      WorkManager.getInstance(getApplicationContext()).enqueue(uploadRequest)
     }
}

        //              val intent = Intent(this, NativeAndroidActivity::class.java)

        // startActivity(intent)
        // result.success(true)
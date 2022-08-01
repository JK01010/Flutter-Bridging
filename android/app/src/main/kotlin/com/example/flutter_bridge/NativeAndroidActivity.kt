package com.example.flutter_bridge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter_bridge.databinding.ActivityNativeAndroidBinding
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class NativeAndroidActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNativeAndroidBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar : Calendar
    private lateinit var alarmManager : AlarmManager
    private lateinit var pendingIntent : PendingIntent

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityNativeAndroidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        binding.selectTimeBtn.setOnClickListener {
            showTimePicker()
        }

        binding.setAlarmBtn.setOnClickListener {
            setAlarm()
        }

        binding.cancelAlarmBtn.setOnClickListener{
            cancelAlarm()
        }

    }


    private fun cancelAlarm(){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent( this,AlarmReceive::class.java)

        pendingIntent = PendingIntent.getBroadcast( this, 0,intent, 0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(this,"Alarm Cancelled",Toast.LENGTH_LONG).show()

    }

    private fun setAlarm(){

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent( this,AlarmReceive::class.java)

        pendingIntent = PendingIntent.getBroadcast( this, 0,intent, 0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
        Toast.makeText(this,"Alarm set Successfully",Toast.LENGTH_LONG).show()

    }

    private fun showTimePicker(){
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        // picker.show(fragmentManager,  "timepicker")

        picker.addOnPositiveButtonClickListener {

            if(picker.hour > 12){

                binding.selectedTime.text = String.format("%02d", picker.hour - 12) + " : " + String.format("%02d",picker.minute) + " PM"

            } else {
                binding.selectedTime.text = String.format("%02d", picker.hour) + " : " + String.format("%02d",picker.minute) + " AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }

    }

    private fun createNotificationChannel(){
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

}


// import android.os.Bundle
// import android.widget.TextView
// import android.widget.Toast
// import io.flutter.app.FlutterActivity
// import io.flutter.plugin.common.MethodChannel


// class NativeAndroidActivity : FlutterActivity() {

//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)
//         setContentView(R.layout.activity_native_android)

//         // val channel = MethodChannel(flutterView, MainActivity.CHANNEL)

//         // findViewById<TextView>(R.id.text_view).setOnClickListener {
//         //     Toast.makeText(this, "Message from native world!", Toast.LENGTH_SHORT).show()
//         //     channel.invokeMethod("message", "Hello from native host")
//         // }
//     }
// }
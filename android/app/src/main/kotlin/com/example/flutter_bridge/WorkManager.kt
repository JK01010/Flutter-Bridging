package com.example.flutter_bridge

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(ctx: Context,params:WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        try {
            for (i in 0..10) {
                Log.i("UploadWorker", "Running a background task time: $i")
            }
            return Result.success();
        }catch (e: Exception) {
            return Result.failure();
        }
        }
    }
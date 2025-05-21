package com.example.gennieus

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationReceiver", "Alarm diterima, akan tampilkan notifikasi")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Buat channel notifikasi (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "daily_reminder",
                "Pengingat Harian",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Pengingat untuk belajar atau jalankan misi"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "daily_reminder")
            .setSmallIcon(R.drawable.kazuha) // Ganti dengan ikon kamu
            .setContentTitle("Pengingat Belajar")
            .setContentText("Hai! Jangan lupa untuk belajar hari ini dan jalankan misimu 💪")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(100, notification)
    }
}

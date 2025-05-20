package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {

    private val POST_HANDLE_DELAY = 3000L
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Contoh: Pindah ke MainActivity setelah delay
        handler.postDelayed({
            val intent = Intent(this@SplashScreen, HalamanAwal::class.java)
            startActivity(intent)
            finish()
        }, POST_HANDLE_DELAY)


    }
}
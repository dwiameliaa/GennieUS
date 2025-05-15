package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HalamanAwal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_awal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonMasuk = findViewById<Button>(R.id.btn_masuk)
        val intent = Intent(this, Login::class.java)

        buttonMasuk.setOnClickListener {
            startActivity(intent)
        }

        val buttonDaftar = findViewById<Button>(R.id.btn_daftar)
        val intent2 = Intent(this, Daftar::class.java)

        buttonDaftar.setOnClickListener {
            startActivity(intent2)
        }

    }
}
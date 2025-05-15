package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LupaPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lupa_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // button back kembali
        val kembaliLogin = findViewById<ImageView>(R.id.iv_kembali)

        kembaliLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // toast email sudah terkirim
        val buttonMasuk = findViewById<Button>(R.id.btn_kirim)

        buttonMasuk.setOnClickListener {
            Toast.makeText(this, "Tautan reset kata sandi telah dikirim ke email Anda", Toast.LENGTH_LONG).show()
        }
    }
}
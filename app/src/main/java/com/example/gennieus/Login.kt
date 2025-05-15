package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // button belum punya akun -> daftar
        val pindahDaftar = findViewById<TextView>(R.id.tv_pindahDaftar)

        pindahDaftar.setOnClickListener {
            val intent = Intent(this, Daftar::class.java)
            startActivity(intent)
        }

        // button kembali back
        val kembalihalamanawal = findViewById<ImageView>(R.id.iv_kembali2)

        kembalihalamanawal.setOnClickListener {
            val intent = Intent(this, HalamanAwal::class.java)
            startActivity(intent)
        }

        // button daftar
        val masukBeranda = findViewById<Button>(R.id.btn_masuk)

        masukBeranda.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        // cek form masuk apakah sudah diisi semua
        val userEditText = findViewById<TextInputEditText>(R.id.et_username)
        val passwordEditText = findViewById<TextInputEditText>(R.id.et_password)
        val buttonMasuk = findViewById<Button>(R.id.btn_masuk)

        // Proses login
        buttonMasuk.setOnClickListener {
            val usernameInput = userEditText.text.toString().trim()
            val passwordInput = passwordEditText.text.toString().trim()

            if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty()) {
                val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
                val savedUsername = sharedPref.getString("username", null)
                val savedPassword = sharedPref.getString("password", null)

                if (usernameInput == savedUsername && passwordInput == savedPassword) {
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Harap isi data dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }


        // page lupa password
        val lupaPass = findViewById<TextView>(R.id.tv_lupaPass)

        lupaPass.setOnClickListener {
            val intent = Intent(this, LupaPassword::class.java)
            startActivity(intent)
        }







    }
}
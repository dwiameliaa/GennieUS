package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Pindah ke halaman daftar
        findViewById<TextView>(R.id.tv_pindahDaftar).setOnClickListener {
            startActivity(Intent(this, Daftar::class.java))
        }

        // Kembali ke halaman awal
        findViewById<ImageView>(R.id.iv_kembali2).setOnClickListener {
            startActivity(Intent(this, HalamanAwal::class.java))
        }

        // Form input
        val emailEditText = findViewById<TextInputEditText>(R.id.et_email)
        val passwordEditText = findViewById<TextInputEditText>(R.id.et_password)
        val buttonMasuk = findViewById<Button>(R.id.btn_masuk)

        // Proses Login
        buttonMasuk.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainMenuActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Email atau kata sandi salah!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Harap isi email dan kata sandi", Toast.LENGTH_SHORT).show()
            }
        }

        // Lupa password
        findViewById<TextView>(R.id.tv_lupaPass).setOnClickListener {
            startActivity(Intent(this, LupaPassword::class.java))
        }

        // notif cek user udah login ke mana
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid
            Log.d("FirebaseUID", "User UID: $uid")
        } else {
            Log.d("FirebaseUID", "User belum login")
        }


    }
}
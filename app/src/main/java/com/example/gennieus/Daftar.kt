package com.example.gennieus

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Daftar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Auto Complete pilihan kelas
        val autoCompleteKelas = findViewById<AutoCompleteTextView>(R.id.autoCompleteKelas)
        val kelas = listOf("Kelas 1", "Kelas 2", "Kelas 3", "Kelas 4", "Kelas 5", "Kelas 6")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, kelas)
        autoCompleteKelas.setAdapter(adapter)

        // supaya langsung muncul saat diklik
        autoCompleteKelas.setOnClickListener {
            autoCompleteKelas.showDropDown()
        }


        // Untuk pindah halaman ke syarat dan ketentuan
        val textView = findViewById<CheckBox>(R.id.cekBox)
        val fullText = "Dengan membuat akun, Anda setuju dengan Syarat & Ketentuan kami"
        val spannableString = SpannableString(fullText)


        // Tentukan bagian teks yang ingin dibuat link
        val clickableText = "Syarat & Ketentuan kami"
        val startIndex = fullText.indexOf(clickableText)
        val endIndex = startIndex + clickableText.length

        // Buat clickable span
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Aksi saat teks diklik ➔ pindah halaman
                val intent = Intent(this@Daftar, SyaratKetentuan::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Tidak pakai garis bawah
                ds.color = Color.BLUE // Warna teks biru
//                ds.color = Color.parseColor("#0099FF")
            }
        }

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT

        // button udah punya akun -> masuk
        val pindahMasuk = findViewById<TextView>(R.id.tv_pindahMasuk)

        pindahMasuk.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // button back
        val kembalihalamanawal = findViewById<ImageView>(R.id.iv_kembali)

        kembalihalamanawal.setOnClickListener {
            val intent = Intent(this, HalamanAwal::class.java)
            startActivity(intent)
        }

        // button daftar
        val masukBeranda = findViewById<Button>(R.id.btn_daftar)

        masukBeranda.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }


        // cek form masuk apakah sudah diisi semua
        // Input field
        val namaLengkap = findViewById<TextInputEditText>(R.id.et_fullname)
        val usernameEditText = findViewById<TextInputEditText>(R.id.et_username)
        val passwordEditText = findViewById<TextInputEditText>(R.id.et_password)
        val emailUser = findViewById<TextInputEditText>(R.id.et_email)
        val pilihKelas = findViewById<AutoCompleteTextView>(R.id.autoCompleteKelas)
        val ceklisSyarat = findViewById<CheckBox>(R.id.cekBox)

        val buttonDaftar = findViewById<Button>(R.id.btn_daftar)

        // Proses daftar
        buttonDaftar.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val nama = namaLengkap.text.toString().trim()
            val email = emailUser.text.toString().trim()
            val kelas = pilihKelas.text.toString().trim()
            val isChecked = ceklisSyarat.isChecked

            if (username.isNotEmpty() && password.isNotEmpty() && nama.isNotEmpty() &&
                email.isNotEmpty() && kelas.isNotEmpty() && isChecked) {

                // Simpan data email dan password ke SharedPreferences
                val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("fullname", nama)
                editor.putString("email", email)
                editor.putString("kelas", kelas)
                editor.apply()

                Toast.makeText(this, "Pendaftaran berhasil! Silakan login.", Toast.LENGTH_SHORT).show()

                // Pindah ke halaman login
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Harap isi data dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }






    }
}
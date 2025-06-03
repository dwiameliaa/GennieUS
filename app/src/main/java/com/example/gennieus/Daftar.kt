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
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Daftar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val autoCompleteKelas = findViewById<AutoCompleteTextView>(R.id.autoCompleteKelas)
        val kelasList = listOf("Kelas 1", "Kelas 2", "Kelas 3", "Kelas 4", "Kelas 5", "Kelas 6")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, kelasList)
        autoCompleteKelas.setAdapter(adapter)
        autoCompleteKelas.setOnClickListener { autoCompleteKelas.showDropDown() }

        val checkBox = findViewById<CheckBox>(R.id.cekBox)
        val fullText = "Dengan membuat akun, Anda setuju dengan Syarat & Ketentuan kami"
        val spannableString = SpannableString(fullText)
        val clickableText = "Syarat & Ketentuan kami"
        val startIndex = fullText.indexOf(clickableText)
        val endIndex = startIndex + clickableText.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@Daftar, SyaratKetentuan::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#0099FF")
            }
        }

        spannableString.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        checkBox.text = spannableString
        checkBox.movementMethod = LinkMovementMethod.getInstance()
        checkBox.highlightColor = Color.TRANSPARENT

        findViewById<TextView>(R.id.tv_pindahMasuk).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        findViewById<ImageView>(R.id.iv_kembali).setOnClickListener {
            startActivity(Intent(this, HalamanAwal::class.java))
        }

        val buttonDaftar = findViewById<Button>(R.id.btn_daftar)

        buttonDaftar.setOnClickListener {
            val fullname = findViewById<TextInputEditText>(R.id.et_fullname).text.toString().trim()
            val username = findViewById<TextInputEditText>(R.id.et_username).text.toString().trim()
            val email = findViewById<TextInputEditText>(R.id.et_email).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.et_password).text.toString().trim()
            val kelas = autoCompleteKelas.text.toString().trim()
            val agree = checkBox.isChecked

            if (email.isNotEmpty() && password.isNotEmpty() && fullname.isNotEmpty() &&
                username.isNotEmpty() && kelas.isNotEmpty() && agree) {

                val firestore = FirebaseFirestore.getInstance()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                            // Buat data user
                            val userData = hashMapOf(
                                "nama" to fullname,
                                "username" to username,
                                "kelas" to kelas,
                                "email" to email
                            )

                            // Simpan ke koleksi "users" dengan dokumen ID = uid
                            firestore.collection("users").document(uid)
                                .set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, Login::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Gagal simpan data: ${e.message}", Toast.LENGTH_LONG).show()
                                }

//                            Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
//                            startActivity(Intent(this, Login::class.java))
//                            finish()
                        } else {
                            Toast.makeText(this, "Gagal daftar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }

                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                    }

            } else {
                Toast.makeText(this, "Harap isi data dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

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
import com.example.gennieus.GamePenjumlahan

class DetailGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView: ImageView = findViewById(R.id.iv_gambar)
        val titleView: TextView = findViewById(R.id.tv_title)
        val descriptionView: TextView = findViewById(R.id.tv_description)

        val imageResId = intent.getIntExtra("imageResId", 0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("fullDescription")

        imageView.setImageResource(imageResId)
        titleView.text = title
        descriptionView.text = description



        // button memainkan permainan
        val buttonPlay: Button = findViewById(R.id.btn_play)

        buttonPlay.setOnClickListener {
            // Pindah ke Game Penjumlahan
            val intent = Intent(this, GamePenjumlahan::class.java)
            startActivity(intent)
        }

        val btnPlay: Button = findViewById(R.id.btn_play)

        btnPlay.setOnClickListener {
            when (title) {
                "Misteri Matematika" -> {
                    val intent = Intent(this, GamePenjumlahan::class.java)
                    startActivity(intent)
                }
//                "Ilmu Pengetahuan Seru" -> {
//                    val intent = Intent(this, GameIlmuPengetahuan::class.java)
//                    startActivity(intent)
//                }
//                "Seni" -> {
//                    val intent = Intent(this, GameSeni::class.java)
//                    startActivity(intent)
//                }
                // Tambahkan kategori lain juga di sini
                else -> {
                    // Default, kalau belum ada gamenya
                    Toast.makeText(this, "Permainan belum tersedia!", Toast.LENGTH_SHORT).show()
                }
            }
        }



        // button back
        val kembaliGame = findViewById<ImageView>(R.id.iv_kembali)

        kembaliGame.setOnClickListener {
            val intent = Intent(this, GameFragment::class.java)
            startActivity(intent)
        }

    }
}
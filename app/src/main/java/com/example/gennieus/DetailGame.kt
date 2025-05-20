package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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



        // untuk menampilkan list game

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewGame)

        if (title == "Misteri Matematika") {
            recyclerView.visibility = View.VISIBLE

            val gameList = listOf(
                GameItem("Penjumlahan Seru", R.drawable.penjumlahan),
                GameItem("Puzzle Angka", R.drawable.puzzleangka),
                GameItem("Hitung Pembelian", R.drawable.pembelian)
            )

            val adapter = GameAdapter(gameList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else if (title == "Petualangan Bahasa") {
            recyclerView.visibility = View.VISIBLE

            val gameList = listOf(
                GameItem("Bermain Kata", R.drawable.image5),
                GameItem("Membaca Seru", R.drawable.image5),
                GameItem("Menulis Kreatif", R.drawable.image5)
            )

            val adapter = GameAdapter(gameList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

        }
        else {
            recyclerView.visibility = View.GONE
        }


    }
}
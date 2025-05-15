package com.example.gennieus


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gennieus.R
import kotlin.random.Random

class GamePenjumlahan : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var btnCheck: Button
    private lateinit var txtResult: TextView

    private val angkaKotak = mutableListOf<Int>()
    private val kotakTerpilih = mutableSetOf<Int>()
    private val daftarButton = mutableListOf<Button>()
    private lateinit var btnExit: Button



    private var totalPercobaan = 0
    private val maxPercobaan = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_penjumlahan)

        gridLayout = findViewById(R.id.gridLayout)
        btnCheck = findViewById(R.id.btnCheck)
        txtResult = findViewById(R.id.txtResult)

        generateKotak()

        btnCheck.setOnClickListener {
            cekHasil()
        }

        btnExit = findViewById(R.id.btnExit)

        btnExit.setOnClickListener {
            val intent = Intent(this, DetailGame::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }


    }


    private fun generateKotak() {
        angkaKotak.clear()
        kotakTerpilih.clear()
        daftarButton.clear()
        gridLayout.removeAllViews()

        for (i in 0 until 12) {
            val angka = Random.nextInt(1, 21)
            angkaKotak.add(angka)

            val button = Button(this).apply {
                text = angka.toString()
                textSize = 24f
                setBackgroundColor(Color.LTGRAY)
                setOnClickListener {
                    togglePilihan(i, this)
                }
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 200
                    height = 200
                    setGravity(Gravity.CENTER)
                }
            }

            daftarButton.add(button)
            gridLayout.addView(button)
        }
    }


    private fun togglePilihan(index: Int, button: Button) {
        if (kotakTerpilih.contains(index)) {
            kotakTerpilih.remove(index)
            button.setBackgroundColor(Color.LTGRAY)
        } else {
            kotakTerpilih.add(index)
            button.setBackgroundColor(Color.CYAN)
        }
    }

    private fun cekHasil() {
        val angkaDipilih = kotakTerpilih.map { angkaKotak[it] }
        val total = angkaDipilih.sum()

        if (angkaDipilih.isEmpty()) {
            txtResult.text = "Pilih dulu minimal 1 kotak!"
            return
        }

        totalPercobaan++

        if (total % 2 == 0) {
            txtResult.text = "Benar! Total $total adalah bilangan genap."
        } else {
            txtResult.text = "Salah! Total $total adalah bilangan ganjil."
        }

        if (totalPercobaan >= maxPercobaan) {
            txtResult.append("\nPermainan selesai!")
            akhiriPermainan()
        } else {
            generateKotak() // buat soal baru
        }
    }


    private fun akhiriPermainan() {
        daftarButton.forEach { it.isEnabled = false }
        btnCheck.isEnabled = false
        btnExit.visibility = View.VISIBLE // Munculkan tombol keluar
    }





}

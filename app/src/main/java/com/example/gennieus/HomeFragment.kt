package com.example.gennieus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnTampilkanLebihBanyak: TextView
    private lateinit var adapter: CardAdapter // ini adapter recyclerview kamu

    private val semuaGame = listOf(
        CardItem(
            R.drawable.image1,
            "Misteri Matematika",
            "Angka dan Hitung",
            "Mempelajari angka-angka ajaib, pola hitungan, dan trik matematika yang seru."
        ),
        CardItem(
            R.drawable.image2,
            "Petualangan Bahasa",
            "Membaca dan Menulis",
            "Belajar membaca, menulis, serta bermain kata untuk meningkatkan keterampilan bahasa."
        ),
        CardItem(
            R.drawable.image3,
            "Ilmu Pengetahuan Seru",
            "Eksperimen Sains",
            "Ayo belajar tentang dunia sains dengan eksperimen seru! Temukan mengapa langit berwarna biru, bagaimana pelangi muncul, dan buat percobaan sederhana yang menyenangkan di rumah."
        ),
        // Tambahkan lebih banyak game sesuai kebutuhan
    )

    private val cardList = mutableListOf<CardItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment_home.xml
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewHome)
        btnTampilkanLebihBanyak = view.findViewById(R.id.more)

        val previewGame = semuaGame.take(2) // Cuma ambil 2 item pertama

        adapter = CardAdapter(previewGame)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter


        btnTampilkanLebihBanyak.setOnClickListener {
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.selectedItemId = R.id.nav_game
        }

        // menampilakan nama lengkap user
        val sharedPref = requireActivity().getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)
        val fullName = sharedPref.getString("fullname", "User") // "User" sebagai default

        val textView = view.findViewById<TextView>(R.id.tv_namaUser)
        textView.text = "$fullName"




        return view
    }
}
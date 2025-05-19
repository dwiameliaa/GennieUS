package com.example.gennieus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter
    private lateinit var cardList: ArrayList<CardItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        // 2 kolom
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)

        cardList = ArrayList()
        loadData()  // fungsi buat isi data
        adapter = CardAdapter(cardList)

        recyclerView.adapter = adapter

        return view
    }

    private fun loadData() {
        // Tambahkan data manual
        cardList.add(
            CardItem(
                R.drawable.image1,
                "Misteri Matematika",
                "Angka dan Hitung",
                "Mempelajari angka-angka ajaib, pola hitungan, dan trik matematika yang seru."
            )
        )
        cardList.add(
            CardItem(
                R.drawable.image3,
                "Petualangan Bahasa",
                "Membaca dan Menulis",
                "Belajar membaca, menulis, serta bermain kata untuk meningkatkan keterampilan bahasa."
            )
        )
        cardList.add(
            CardItem(
                R.drawable.image2,
                "Ilmu Pengetahuan Seru",
                "Eksperimen Sains",
                "Ayo belajar tentang dunia sains dengan eksperimen seru! Temukan mengapa langit berwarna biru, bagaimana pelangi muncul, dan buat percobaan sederhana yang menyenangkan di rumah."
            )
        )

        cardList.add(
            CardItem(
                R.drawable.image4,
                "Seni",
                "Kreativitas",
                "Tunjukkan bakatmu dalam menggambar, mewarnai, dan membuat kerajinan tangan! Dunia seni penuh warna siap kamu jelajahi dengan berbagai aktivitas kreatif."
            )
        )

        cardList.add(
            CardItem(
                R.drawable.image5,
                "Serunya Belajar Sejarah",
                "Kisah Pahlawan dan Zaman Dahulu",
                "Mari mengenal para pahlawan hebat dan cerita seru dari masa lalu. Kita akan belajar bagaimana kehidupan zaman dulu dan mengapa sejarah itu penting untuk masa depan."
            )
        )

        cardList.add(
            CardItem(
                R.drawable.image7,
                "Teka-Teki Seru",
                "Melatih Otak dengan Permainan",
                "Suka tantangan? Yuk pecahkan berbagai teka-teki, puzzle, dan permainan asah otak seru yang membuatmu semakin pintar dan cepat berpikir!"
            )
        )

        cardList.add(
            CardItem(
                R.drawable.image6,
                "Menjelajah Alam",
                "Hewan, Tumbuhan, dan Petualangan",
                "Bersiaplah berpetualang di alam bebas! Kenali hewan-hewan unik, tanaman-tanaman menakjubkan, dan rahasia alam yang luar biasa."
            )
        )

        cardList.add(
            CardItem(
                R.drawable.image8,
                "Teknologi Asyik",
                "Robot, Komputer, dan Penemuan",
                "Mau tahu bagaimana robot bekerja? Atau siapa yang menciptakan komputer? Yuk jelajahi dunia teknologi yang seru dan penuh penemuan hebat!"
            )
        )

        // Bisa ditambah lagi...
    }
}

package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PengaturanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pengaturan, container, false)

//        val editProfil = view.findViewById<ImageView>(R.id.iv_btnEdit)
        val editProfil = view.findViewById<Button>(R.id.btn_edit)

        editProfil.setOnClickListener {
            // Ganti fragment manual
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, ProfilFragment())
            fragmentTransaction.addToBackStack(null) // supaya bisa klik tombol back
            fragmentTransaction.commit()
        }

        val gantiPass = view.findViewById<CardView>(R.id.cv_pass)

        gantiPass.setOnClickListener {
            // Ganti fragment manual
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, GantiPasswordFragment())
            fragmentTransaction.addToBackStack(null) // supaya bisa klik tombol back
            fragmentTransaction.commit()
        }

//        // menampilakan nama lengkap user
//        val sharedPreff = requireActivity().getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)
//        val fullName = sharedPreff.getString("fullname", "User") // "User" sebagai default
//
//        val textView = view.findViewById<TextView>(R.id.tv_namaUser)
//        textView.text = "$fullName"

        // menampilkan nama lengkap user
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        val textView = view.findViewById<TextView>(R.id.tv_namaUser)

        if (uid != null) {
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val fullName = document.getString("nama") ?: "User"
                        textView.text = fullName
                    } else {
                        textView.text = "User"
                    }
                }
                .addOnFailureListener {
                    textView.text = "User"
                }
        }

        // keluar dari info login saat ini, jadi akan pergi halaman awal
        val buttonKeluar = view.findViewById<CardView>(R.id.cv_keluar)

        buttonKeluar.setOnClickListener {
//            val intent = Intent(requireActivity(), HalamanAwal::class.java)
//            startActivity(intent)

            AlertDialog.Builder(requireContext())
                .setTitle("Keluar dari Akun?")
                .setMessage("Anda akan keluar dari info login saat ini. Apakah Anda yakin ingin melanjutkan?")
                .setPositiveButton("Ya") { _, _ ->

                    // Navigasi ke LoginActivity (atau halaman lain)
                    val intent = Intent(requireContext(), HalamanAwal::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()

        }

        // ke menu hapus akun
        val buttonHapusAkun = view.findViewById<CardView>(R.id.cv_hapusAkun)

        buttonHapusAkun.setOnClickListener {
            // Ganti fragment manual
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, HapusAkunFragment())
            fragmentTransaction.addToBackStack(null) // supaya bisa klik tombol back
            fragmentTransaction.commit()
        }

        // ke menu notif , ketambahan fragment notif, notif receiver
        val notifikasi = view.findViewById<CardView>(R.id.cv_notif)

        notifikasi.setOnClickListener {
            // Ganti fragment manual
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, NotifikasiFragment())
            fragmentTransaction.addToBackStack(null) // supaya bisa klik tombol back
            fragmentTransaction.commit()
        }

        return view
    }
}

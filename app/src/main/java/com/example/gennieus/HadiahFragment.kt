package com.example.gennieus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HadiahFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment_hadiah.xml
        val view = inflater.inflate(R.layout.fragment_hadiah, container, false)

        val buttonAmbil = view.findViewById<Button>(R.id.btn_ambil)
        val sharedPref = requireContext().getSharedPreferences("ButtonPrefs", 0)

        // Cek apakah button sudah pernah dinonaktifkan
        val isButtonDisabled = sharedPref.getBoolean("button_disabled", false)

        if (isButtonDisabled) {
            buttonAmbil.isEnabled = false
            buttonAmbil.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
            buttonAmbil.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            buttonAmbil.setOnClickListener {
                Toast.makeText(requireContext(), "Hadiah sudah diambil", Toast.LENGTH_SHORT).show()

                // Membuat button tidak bisa diklik lagi
                buttonAmbil.isEnabled = false
                buttonAmbil.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                buttonAmbil.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                // Simpan status ke SharedPreferences
                sharedPref.edit {
                    putBoolean("button_disabled", true)
                    apply() // <-- jangan lupa apply() kalau pakai edit { }
                }
            }
        }

        // menampilkan nama lengkap user
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        val textView = view.findViewById<TextView>(R.id.tv_namaUser)
        val tvPoin = view.findViewById<TextView>(R.id.tv_poin)

        if (uid != null) {
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val fullName = document.getString("nama") ?: "User"
                        val poin = document.getLong("poin") ?: 0

                        textView.text = fullName
                        tvPoin.text = "$poin"
                    } else {
                        textView.text = "User"
                    }
                }
                .addOnFailureListener {
                    textView.text = "User"
                }
        }

        return view
    }
}

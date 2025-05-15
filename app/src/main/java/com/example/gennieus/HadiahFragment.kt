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

        // menampilakan nama lengkap user
        val sharedPreff = requireActivity().getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)
        val fullName = sharedPreff.getString("fullname", "User") // "User" sebagai default

        val textView = view.findViewById<TextView>(R.id.tv_namaUser)
        textView.text = "$fullName"

        return view
    }
}

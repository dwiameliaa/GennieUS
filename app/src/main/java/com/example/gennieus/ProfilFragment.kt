package com.example.gennieus

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class ProfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        // Auto Complete pilihan kelas
        val autoCompleteKelas = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteKelas)
        val kelas = listOf("Kelas 1", "Kelas 2", "Kelas 3", "Kelas 4", "Kelas 5", "Kelas 6")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, kelas)
        autoCompleteKelas.setAdapter(adapter)

        // supaya langsung muncul saat diklik
        autoCompleteKelas.setOnClickListener {
            autoCompleteKelas.showDropDown()
        }

        // tombol kembali ke fragment pengaturan
        val ivKembali = view.findViewById<ImageView>(R.id.iv_kembali)

        ivKembali.setOnClickListener {
            parentFragmentManager.popBackStack()

            // Tampilkan lagi BottomNavigationView
            val bottomNav =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNav.visibility = View.VISIBLE

        }

        // Sembunyikan BottomNavigationView
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE


        // Menampilkan data user dari SharedPreferences
        val sharedPref =
            requireActivity().getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)

        val fullName = sharedPref.getString("fullname", "User") ?: "User"
        val emailUser = sharedPref.getString("email", "user@gmail.com") ?: "user@gmail.com"
        val username = sharedPref.getString("username", "user") ?: "user"
        val kelass = sharedPref.getString("kelas", "Kelas 3") ?: "Kelas 3"

// Temukan view dari layout
        val etFullname = view.findViewById<TextInputEditText>(R.id.et_fullname)
        val etUsername = view.findViewById<TextInputEditText>(R.id.et_username)
        val etEmail = view.findViewById<TextInputEditText>(R.id.et_email)
        val autoCompleteKelass = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteKelas)

// Set nilai ke input field
        etFullname.text = Editable.Factory.getInstance().newEditable(fullName)
        etUsername.text = Editable.Factory.getInstance().newEditable(username)
        etEmail.text = Editable.Factory.getInstance().newEditable(emailUser)
        autoCompleteKelass.setText(kelass, false) // false: tidak memicu dropdown terbuka

        // Tombol simpan perubahan
        val btnSimpan = view.findViewById<Button>(R.id.btn_simpan)

        btnSimpan.setOnClickListener {

            val newFullName = etFullname.text.toString().trim()
            val newUsername = etUsername.text.toString().trim()
            val newEmail = etEmail.text.toString().trim()
            val newKelas = autoCompleteKelass.text.toString().trim()


            // Validasi sederhana
            if (newFullName.isBlank() || newUsername.isBlank() || newEmail.isBlank() || newKelas.isBlank()) {
                Toast.makeText(requireContext(), "Harap isi dengan lengkap", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan perubahan ke SharedPreferences
                with(sharedPref.edit()) {
                    putString("fullname", newFullName)
                    putString("username", newUsername)
                    putString("email", newEmail)
                    putString("kelas", newKelas)


                    apply()
                }

                Toast.makeText(requireContext(), "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show()
            }

//            ini untuk yang activity biasa
//            Toast.makeText(this, "Harap isi data dengan lengkap", Toast.LENGTH_SHORT).show()
        }



        return view

    }
}


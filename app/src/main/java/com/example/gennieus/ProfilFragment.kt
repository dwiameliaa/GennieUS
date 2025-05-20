package com.example.gennieus

import android.os.Bundle
import android.text.Editable
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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


        // menampilkan data user
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        val etFullname = view.findViewById<TextInputEditText>(R.id.et_fullname)
        val etUsername = view.findViewById<TextInputEditText>(R.id.et_username)
//        val emailEditText = view.findViewById<TextInputEditText>(R.id.et_email)
        val autoCompleteKelass = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteKelas)

        if (uid != null) {
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val fullName = document.getString("nama") ?: "User"
                        val username = document.getString("username") ?: ""
//                        val email = document.getString("email") ?: ""
                        val kelas = document.getString("kelas") ?: ""

                        etFullname.setText(fullName)
                        etUsername.setText(username)
//                        emailEditText.setText(email)
                        autoCompleteKelass.setText(kelas)
                    } else {
                        // Data tidak ditemukan
                        Toast.makeText(context, "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Gagal mengambil data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }



        val btnSimpan = view.findViewById<Button>(R.id.btn_simpan)

        btnSimpan.setOnClickListener {
            val newFullName = etFullname.text.toString().trim()
            val newUsername = etUsername.text.toString().trim()
//            val newEmail = etEmail.text.toString().trim()
            val newKelas = autoCompleteKelass.text.toString().trim()

            if (newFullName.isBlank() || newUsername.isBlank() || newKelas.isBlank()) {
                Toast.makeText(requireContext(), "Harap isi dengan lengkap", Toast.LENGTH_SHORT).show()
            } else {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (uid != null) {
                    val db = FirebaseFirestore.getInstance()
                    val userRef = db.collection("users").document(uid)

                    val updatedData = mapOf(
                        "nama" to newFullName,
                        "username" to newUsername,
//                        "email" to newEmail,
                        "kelas" to newKelas
                    )

                    userRef.update(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Gagal menyimpan: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }




        return view

    }
}


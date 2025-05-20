package com.example.gennieus

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class GantiPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ganti_password, container, false)


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


        // Tombol simpan perubahan
        val btnGanti = view.findViewById<Button>(R.id.btn_ganti)

        val passwordEditText = view.findViewById<TextInputEditText>(R.id.et_password)
        val passwordBaru = view.findViewById<TextInputEditText>(R.id.et_passwordbaru)
        val passwordBaruUlang = view.findViewById<TextInputEditText>(R.id.et_passwordbaruUlang)

        btnGanti.setOnClickListener {
            val passwordLama = passwordEditText.text.toString().trim()
            val passwordBaru1 = passwordBaru.text.toString().trim()
            val passwordBaru2 = passwordBaruUlang.text.toString().trim()

            val user = FirebaseAuth.getInstance().currentUser

            if (passwordLama.isNotEmpty() && passwordBaru1.isNotEmpty() && passwordBaru2.isNotEmpty()) {

                if (passwordBaru1 != passwordBaru2) {
                    Toast.makeText(requireContext(), "Konfirmasi kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider.getCredential(user.email!!, passwordLama)

                    user.reauthenticate(credential)
                        .addOnSuccessListener {
                            user.updatePassword(passwordBaru1)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Kata sandi berhasil diganti!", Toast.LENGTH_SHORT).show()
                                    parentFragmentManager.popBackStack()

                                    // Tampilkan kembali bottom nav
                                    val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                                    bottomNav.visibility = View.VISIBLE
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(requireContext(), "Gagal mengganti kata sandi: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Autentikasi ulang gagal: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    Toast.makeText(requireContext(), "User tidak ditemukan. Silakan login kembali.", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }


        // page lupa password
        val lupaPass = view.findViewById<TextView>(R.id.tv_lupaPass)

        lupaPass.setOnClickListener {
            Log.d("DEBUG", "Tombol lupa password diklik")
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, LupaPassFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }



        return view

    }
}


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

            val passwordInput = passwordEditText.text.toString().trim()
            val passwordInputBaru = passwordBaru.text.toString().trim()
            val passwordInputBaruUlang = passwordBaruUlang.text.toString().trim()

            if (passwordInput.isNotEmpty() && passwordInputBaru.isNotEmpty() && passwordInputBaruUlang.isNotEmpty()) {
                val sharedPref = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
                val savedPassword = sharedPref.getString("password", null)

                // Simpan perubahan ke SharedPreferences
                with(sharedPref.edit()) {
                    putString("password", passwordInputBaru)
                    apply()
                }

                if (passwordInput == savedPassword) {
                    parentFragmentManager.popBackStack()
                    Toast.makeText(requireContext(), "Kata sandi berhasil diganti!", Toast.LENGTH_SHORT).show()

                    // Tampilkan lagi BottomNavigationView
                    val bottomNav =
                        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                    bottomNav.visibility = View.VISIBLE

                } else {
                    Toast.makeText(requireContext(), "Kata sandi lama anda salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Harap isi data dengan lengkap", Toast.LENGTH_SHORT).show()
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


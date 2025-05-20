package com.example.gennieus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LupaPassFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lupa_pass, container, false)

        val emailEditText = view.findViewById<TextInputEditText>(R.id.et_email)
        val btnResetPassword = view.findViewById<Button>(R.id.btn_kirim)

        btnResetPassword.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Masukkan email Anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Email untuk reset kata sandi telah dikirim ke $email",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengirim email reset: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }


        // tombol kembali ke fragment ganti password
        val ivKembali = view?.findViewById<ImageView>(R.id.iv_kembali)

        ivKembali?.setOnClickListener {
            parentFragmentManager.popBackStack()

            // Tampilkan lagi BottomNavigationView
            val bottomNav =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNav.visibility = View.VISIBLE

        }

        // Sembunyikan BottomNavigationView
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE

        return view
    }

}

package com.example.gennieus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HapusAkunFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hapus_akun, container, false)


        val etPassword = view.findViewById<TextInputEditText>(R.id.et_password)
        val btnHapus = view.findViewById<Button>(R.id.btn_hapus_akun)

        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email

        btnHapus.setOnClickListener {
            val password = etPassword.text.toString().trim()

            if (email.isNullOrEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Masukkan kata sandi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Konfirmasi hapus akun
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Hapus Akun")
                .setMessage("Apakah Anda yakin ingin menghapus akun ini secara permanen?")
                .setPositiveButton("Ya") { _, _ ->
                    // Re-authenticate
                    val credential = EmailAuthProvider.getCredential(email, password)
                    user?.reauthenticate(credential)
                        ?.addOnSuccessListener {
                            val uid = user.uid

                            // Hapus data user dari Firestore
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(uid)
                                .delete()
                                .addOnSuccessListener {
                                    // Lanjut hapus akun dari Firebase Auth
                                    user.delete()
                                        .addOnSuccessListener {
                                            Toast.makeText(requireContext(), "Akun berhasil dihapus", Toast.LENGTH_SHORT).show()

                                            // Arahkan ke halaman awal/login
                                            val intent = Intent(requireContext(), HalamanAwal::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(requireContext(), "Gagal hapus akun: ${it.message}", Toast.LENGTH_LONG).show()
                                        }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(), "Gagal hapus data Firestore: ${it.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                        ?.addOnFailureListener {
                            Toast.makeText(requireContext(), "Re-authentication gagal: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                }
                .setNegativeButton("Batal", null)
                .show()
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

        return view
    }
}

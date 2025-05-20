package com.example.gennieus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PeringkatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_peringkat, container, false)

        // menampilkan nama lengkap user
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        val textView = view.findViewById<TextView>(R.id.tv_kazuha)
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


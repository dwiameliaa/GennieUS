package com.example.gennieus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PeringkatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_peringkat, container, false)

        val sharedPref = requireActivity().getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)
        val fullName = sharedPref.getString("fullname", "User") // "User" sebagai default

        val textView = view.findViewById<TextView>(R.id.tv_kazuha)
        textView.text = "$fullName"

        return view
    }
}


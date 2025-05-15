package com.example.gennieus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.content.edit

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("ButtonPrefs", 0)
        sharedPref.edit {
            putBoolean("button_disabled", false)
            apply()
        }

        // Tambahkan ini untuk menampilkan HomeFragment
        if (savedInstanceState == null) { // Supaya tidak recreate fragment saat rotate screen
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()) // pastikan ID nya sesuai container
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_hadiah -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HadiahFragment())
                        .commit()
                    true
                }
                R.id.nav_game -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, GameFragment())
                        .commit()
                    true
                }
                R.id.nav_peringkat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PeringkatFragment())
                        .commit()
                    true
                }
                R.id.nav_pengaturan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PengaturanFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

    }
}
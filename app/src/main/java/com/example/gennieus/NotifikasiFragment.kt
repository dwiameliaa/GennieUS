package com.example.gennieus

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class NotifikasiFragment : Fragment() {

    private lateinit var checkboxAktivitas: CheckBox
    private lateinit var tvWaktuLabel: TextView
    private lateinit var btnPilihWaktu: Button
    private lateinit var tvWaktuTerpilih: TextView

    // Untuk menyimpan jam dan menit yang dipilih user
    private var selectedHour = 9
    private var selectedMinute = 0

    // Firebase Firestore instance
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifikasi, container, false)

        checkboxAktivitas = view.findViewById(R.id.checkbox_aktivitas)
        tvWaktuLabel = view.findViewById(R.id.tv_waktu_label)
        btnPilihWaktu = view.findViewById(R.id.btn_pilih_waktu)
        tvWaktuTerpilih = view.findViewById(R.id.tv_waktu_terpilih)

        // Load data dari Firestore saat mulai fragment
        loadSettingsFromFirestore()

        // Toggle visibility komponen waktu berdasarkan checkbox
        checkboxAktivitas.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvWaktuLabel.visibility = View.VISIBLE
                btnPilihWaktu.visibility = View.VISIBLE
                tvWaktuTerpilih.visibility = View.VISIBLE
            } else {
                tvWaktuLabel.visibility = View.GONE
                btnPilihWaktu.visibility = View.GONE
                tvWaktuTerpilih.visibility = View.GONE

                // Jika dimatikan, hapus jadwal notifikasi
                cancelScheduledNotification()
                saveSettingsToFirestore(isChecked, selectedHour, selectedMinute)
            }
        }

        btnPilihWaktu.setOnClickListener {
            showTimePickerDialog()
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

    private fun showTimePickerDialog() {
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                selectedHour = hourOfDay
                selectedMinute = minute
                tvWaktuTerpilih.text = String.format("Waktu terpilih: %02d:%02d", hourOfDay, minute)

                // Simpan setting dan jadwalkan ulang notifikasi
                saveSettingsToFirestore(true, selectedHour, selectedMinute)
                scheduleDailyReminder(selectedHour, selectedMinute)
            },
            selectedHour,
            selectedMinute,
            true
        )
        timePicker.show()
    }

    private fun loadSettingsFromFirestore() {
        if (userId == null) return

        firestore.collection("users").document(userId).collection("settings")
            .document("notifications")
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val isAktif = doc.getBoolean("aktifkan_misi") ?: false
                    val jam = (doc.getLong("jam") ?: 9L).toInt()
                    val menit = (doc.getLong("menit") ?: 0L).toInt()

                    checkboxAktivitas.isChecked = isAktif
                    selectedHour = jam
                    selectedMinute = menit

                    tvWaktuLabel.visibility = if (isAktif) View.VISIBLE else View.GONE
                    btnPilihWaktu.visibility = if (isAktif) View.VISIBLE else View.GONE
                    tvWaktuTerpilih.visibility = if (isAktif) View.VISIBLE else View.GONE
                    tvWaktuTerpilih.text = String.format("Waktu terpilih: %02d:%02d", jam, menit)

                    if (isAktif) {
                        scheduleDailyReminder(jam, menit)
                    } else {
                        cancelScheduledNotification()
                    }
                }
            }
            .addOnFailureListener {
                Log.e("NotifikasiFragment", "Gagal load setting notifikasi", it)
            }
    }

    private fun saveSettingsToFirestore(aktif: Boolean, jam: Int, menit: Int) {
        if (userId == null) return

        val data = hashMapOf(
            "aktifkan_misi" to aktif,
            "jam" to jam,
            "menit" to menit
        )
        firestore.collection("users").document(userId)
            .collection("settings").document("notifications")
            .set(data)
            .addOnSuccessListener {
                Log.d("NotifikasiFragment", "Setting notifikasi berhasil disimpan")
            }
            .addOnFailureListener {
                Log.e("NotifikasiFragment", "Gagal simpan setting notifikasi", it)
            }
    }

    private fun scheduleDailyReminder(hour: Int, minute: Int) {
        val context = requireContext()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Log.d("NotifikasiFragment", "Notifikasi dijadwalkan setiap hari pukul $hour:$minute")
    }

    private fun cancelScheduledNotification() {
        val context = requireContext()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        Log.d("NotifikasiFragment", "Notifikasi dibatalkan")
    }
}

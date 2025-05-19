package com.example.gennieus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(private val gameList: List<GameItem>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tv_gameTitle)
        val imageView: ImageView = itemView.findViewById(R.id.iv_gameImage)
        val playButton: Button = itemView.findViewById(R.id.btn_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_game, parent, false) // ini layout desainmu
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.titleTextView.text = game.title
        holder.imageView.setImageResource(game.imageResId)

        holder.playButton.setOnClickListener {
            val context = holder.itemView.context

            when (game.title) {
                "Penjumlahan Seru" -> {
                    val intent = Intent(context, GamePenjumlahan::class.java)
                    context.startActivity(intent)
                }
//                "Puzzle Angka" -> {
//                    val intent = Intent(context, GameIlmuPengetahuan::class.java)
//                    context.startActivity(intent)
//                }
//                "Hitung Pembelian" -> {
//                    val intent = Intent(context, GameSeni::class.java)
//                    context.startActivity(intent)
//                }
                else -> {
                    Toast.makeText(context, "Permainan belum tersedia!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount() = gameList.size
}

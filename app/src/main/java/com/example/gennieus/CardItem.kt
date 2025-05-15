package com.example.gennieus

data class CardItem(
    val imageResId: Int,
    val title: String,
    val shortDescription: String,   // Untuk RecyclerView
    val fullDescription: String     // Untuk DetailGame
)

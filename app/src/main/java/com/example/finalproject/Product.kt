package com.example.finalproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Product(

    val category: String,
    val name: String,
    val image: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0
)

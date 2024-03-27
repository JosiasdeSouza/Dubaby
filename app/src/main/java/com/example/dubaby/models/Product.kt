package com.example.dubaby.models

import com.example.dubaby.models.Rating



data class Product(
    val id: Int = 0,
    val image: String = "",
    val price: Double = 0.0,
    val rating: Rating? = null, // Assuming 'rating' could be absent
    val title: String = ""
)

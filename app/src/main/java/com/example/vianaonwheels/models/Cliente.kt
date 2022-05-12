package com.example.vianaonwheels.models

import java.util.*

data class Cliente(
    val name: String,
    val age: Int,
    val nif: Int,
    val email: String,
    val password: String,
    val birth_date: Date,
    val img: String,
    val phone_number: Int
)

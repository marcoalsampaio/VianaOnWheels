package com.example.vianaonwheels.models

import java.util.*

data class InsertTicket(
    val price: String,
    val dates: String,
    val origin_hour: String,
    val company: String,
    val destiny: String,
    val origin: String,
    val qtd: String,
    val destiny_hour: String,
    val email: String,
    val used: String
)

package com.example.vianaonwheels.models

import java.util.*

data class User(
    var name: String,
    var email: String,
    var pass: String,
    var img: String?,
    var birthDate: String?,
    var phoneNumber: Int?,
    var nif: Int?
    )


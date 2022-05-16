package com.example.vianaonwheels.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {


    @POST("/sendemail")
    fun sendEmail(@Query("token") token: String? ):Call<OutputPost>
}
package com.example.vianaonwheels.models;

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*;

public class Trips(
    var date: String,
    var basePrice: Float,
    var beginHour: String,
    var endHour: String,
    var company: String,
    var connections: Int,
    var destiny: String,
    var totalKms: Int,
    var origin: String,
){

}


package com.example.vianaonwheels.models;

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*;

public class Historic(
    var beginhour: String,
    var destinyHour: String,
    var company: String,
    var date_hour: String,
    var destination: String,
    var origin: String?,
    var price: String,
    var c_email: String,
){

}


package com.example.vianaonwheels.adapters

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.ToUse
import java.util.*
import kotlin.collections.ArrayList

class ToUseAdapter(private val touse: ArrayList<ToUse>): RecyclerView.Adapter<ToUseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToUseViewHolder {
        return ToUseViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.touse_line,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ToUseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return touse.size
    }

}



class ToUseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val price = itemView.findViewById<TextView>(R.id.tv_price_touse)
    val dates = itemView.findViewById<TextView>(R.id.tv_date_touse)
    val hours = itemView.findViewById<TextView>(R.id.tv_hours_touse)
    val company = itemView.findViewById<TextView>(R.id.tv_company_touse)
    val destiny = itemView.findViewById<TextView>(R.id.tv_destiny_touse)
    val origin = itemView.findViewById<TextView>(R.id.tv_origin_touse)
}
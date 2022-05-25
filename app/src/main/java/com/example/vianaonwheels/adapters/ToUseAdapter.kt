package com.example.vianaonwheels.adapters

import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.ToUse
import kotlin.collections.ArrayList

class ToUseAdapter(private val tickets: ArrayList<ToUse>): RecyclerView.Adapter<ToUseViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToUseViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.touse_line,
                parent,
                false
            )

        return ToUseViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ToUseViewHolder, position: Int) {

        return holder.bind(tickets[position])
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    fun getItem(position: Int): ToUse {
        return tickets[position]
    }

}

class ToUseViewHolder(itemView: View, listener: ToUseAdapter.onItemClickListener): RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener{
            listener.onItemClick(adapterPosition)
        }
    }

    fun bind(ticket: ToUse) {
        price.text = ticket.price
        dates.text = ticket.dates
        hours.text = ticket.hours
        company.text = ticket.company
        destiny.text = ticket.destiny
        origin.text = ticket.origin

    }

    private val price = itemView.findViewById<TextView>(R.id.tv_price_touse)
    private val dates = itemView.findViewById<TextView>(R.id.tv_date_touse)
    private val hours = itemView.findViewById<TextView>(R.id.tv_hours_touse)
    private val company = itemView.findViewById<TextView>(R.id.tv_company_touse)
    private val destiny = itemView.findViewById<TextView>(R.id.tv_destiny_touse)
    private val origin = itemView.findViewById<TextView>(R.id.tv_origin_touse)
}


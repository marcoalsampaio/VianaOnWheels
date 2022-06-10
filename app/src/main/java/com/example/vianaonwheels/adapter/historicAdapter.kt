package com.example.vianaonwheels.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.Historic
import java.util.*

class HistoricAdapter(private val list: ArrayList<Historic>): RecyclerView.Adapter<HistoricAdapter.HistoricViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricViewHolder {
        return HistoricViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        val current = list[position]
        holder.bind(current.company, current.date_hour, current.origin, current.destination, current.price, current.beginhour, current.destinyHour)
    }

    class HistoricViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val companyItemView: TextView = itemView.findViewById(R.id.tv_company)
        private val dateItemView: TextView = itemView.findViewById(R.id.tv_date)
        private val hourItemView: TextView = itemView.findViewById(R.id.tv_hours)
        private val originItemView: TextView = itemView.findViewById(R.id.tv_origin)
        private val destinationItemView: TextView = itemView.findViewById(R.id.tv_destiny)
        private val priceItemView: TextView = itemView.findViewById(R.id.tv_priceValue)
        fun bind(company: String?, date_hour: String, origin: String?, destination: String, price: String, beginHour: String, destinyHour: String) {

            companyItemView.text = companyItemView.text.toString() + ' ' + company
            dateItemView.text =' ' +  dateItemView.text.toString() + date_hour
            hourItemView.text = hourItemView.text.toString() + ' ' + beginHour
            originItemView.text = originItemView.text.toString() +  ' ' + origin
            destinationItemView.text = destinationItemView.text.toString() + ' ' + destination
            priceItemView.text = price.toString() + '€'


        }

        companion object {
            fun create(parent: ViewGroup): HistoricViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_historic_item, parent, false)
                return HistoricViewHolder(view)
            }
        }
    }

    class HistoricComparator : DiffUtil.ItemCallback<Historic>() {
        override fun areItemsTheSame(oldItem: Historic, newItem: Historic): Boolean {
            return oldItem === newItem

        }

        override fun areContentsTheSame(oldItem: Historic, newItem: Historic): Boolean {
            return oldItem.c_email == newItem.c_email
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
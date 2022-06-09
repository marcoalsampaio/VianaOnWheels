package com.example.vianaonwheels.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.CartItems
import com.example.vianaonwheels.models.Trips
import com.google.android.material.floatingactionbutton.FloatingActionButton


class IntercitiesAdapter(private val list: ArrayList<Trips>, val cartCountItemView: TextView, val cartPrice: TextView, val cart: ArrayList<CartItems>): RecyclerView.Adapter<IntercitiesAdapter.IntercitiesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercitiesViewHolder {

        return IntercitiesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: IntercitiesViewHolder, position: Int) {
        val current = list[position]
        holder.beginHourItemView.text = current.beginHour
        holder.endHourItemView.text = current.endHour
        holder.totalKMSItemView.text = current.totalKms.toString() + " kms"
        holder.originItemView.text = current.origin
        holder.destinyItemView.text = current.destiny
        holder.connectionsItemView.text = current.connections.toString() + " conexão"

        for(item in cart){
            if (item.ida == current.date && item.destino == current.destiny && item.origem == current.origin && item.company == current.company && item.beginHour == current.beginHour && item.endHour == current.endHour){
                holder.buttonItemView.isVisible = false
                holder.buttonRemoveItemView.isVisible = true
            }
        }

        holder.buttonItemView.setOnClickListener {
            addItem(current.date, current.origin, current.destiny, current.basePrice, holder.preferences, holder, current.endHour, current.company, current.beginHour)

            holder.buttonItemView.isVisible = false
            holder.buttonRemoveItemView.isVisible = true

        }
        holder.buttonRemoveItemView.setOnClickListener {

            removeItem(current, holder, position)
            holder.buttonRemoveItemView.isVisible = false
            holder.buttonItemView.isVisible = true

        }

    }


    class IntercitiesComparator : DiffUtil.ItemCallback<Trips>() {
        override fun areItemsTheSame(oldItem: Trips, newItem: Trips): Boolean {
            return oldItem === newItem

        }

        override fun areContentsTheSame(oldItem: Trips, newItem: Trips): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addItem(date: String, origin: String, destiny: String, basePrice: Float, preferences: SharedPreferences, holder: IntercitiesViewHolder,  endHour: String, company: String, beginHour: String){

        cart.add(CartItems(date, origin, destiny, basePrice, preferences.getInt("ad_tickets", -1), preferences.getInt("jo_tickets", -1), preferences.getInt("se_tickets", -1), beginHour, endHour, company ))
        cartCountItemView.text = holder.itemView.context.getString(R.string.icities_search_cart) +  cart.size
        var totalPrice = 0.0
        for(item in cart){
            totalPrice += item.preco.toDouble()
        }
        cartPrice.text = holder.itemView.context.getString(R.string.icities_search_price) + totalPrice.toString() + "€"
    }

    fun removeItem( trip: Trips, holder: IntercitiesViewHolder, position: Int){


        val iterator = cart.iterator()
        while(iterator.hasNext()){
            val item = iterator.next()
            if(item.ida == trip.date && item.destino == trip.destiny && item.origem == trip.origin && item.company == trip.company && item.beginHour == trip.beginHour && item.endHour == trip.endHour){
                iterator.remove()
            }
        }
        cartCountItemView.text = holder.itemView.context.getString(R.string.icities_search_cart) +  cart.size
        var totalPrice = 0.00
        for(item in cart){
            totalPrice += item.preco.toDouble()
        }
        cartPrice.text = holder.itemView.context.getString(R.string.icities_search_price) + totalPrice.toString() + "€"
    }

    class IntercitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val preferences = itemView.context.getSharedPreferences("TICKETS_COUNT", Context.MODE_PRIVATE)
        val beginHourItemView: TextView = itemView.findViewById(R.id.tv_hourB)
        val endHourItemView: TextView = itemView.findViewById(R.id.tv_hourE)
        val originItemView: TextView = itemView.findViewById(R.id.tv_BPlace)
        val destinyItemView: TextView = itemView.findViewById(R.id.tv_EPlace)
        val connectionsItemView: TextView = itemView.findViewById(R.id.connections)
        val totalKMSItemView: TextView = itemView.findViewById(R.id.tv_time_dist)
        val buttonItemView: Button = itemView.findViewById(R.id.btn_add)
        val buttonRemoveItemView: Button = itemView.findViewById(R.id.btn_remove)






        companion object {
            fun create(parent: ViewGroup): IntercitiesViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return IntercitiesViewHolder(view)
            }
        }

    }



}
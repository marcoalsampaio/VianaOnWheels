package com.example.vianaonwheels.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.IntercitiesSearch.Companion.cart
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.CartItems
import com.example.vianaonwheels.models.Trips


class IntercitiesAdapter(private val list: ArrayList<Trips>): RecyclerView.Adapter<IntercitiesAdapter.IntercitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercitiesViewHolder {
        return IntercitiesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: IntercitiesViewHolder, position: Int) {
        val current = list[position]
        holder.bind(current.company, current.beginHour, current.endHour, current.origin, current.destiny, current.connections, current.totalKms, current.basePrice )
    }

    class IntercitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val preferences = itemView.context.getSharedPreferences("TICKETS_COUNT", Context.MODE_PRIVATE)
        private val beginHourItemView: TextView = itemView.findViewById(R.id.tv_hourB)
        private val endHourItemView: TextView = itemView.findViewById(R.id.tv_hourE)
        private val originItemView: TextView = itemView.findViewById(R.id.tv_BPlace)
        private val destinyItemView: TextView = itemView.findViewById(R.id.tv_EPlace)
        private val connectionsItemView: TextView = itemView.findViewById(R.id.connections)
        private val totalKMSItemView: TextView = itemView.findViewById(R.id.tv_time_dist)
        private val buttonItemView: Button = itemView.findViewById(R.id.btn_add)
        private val cartCountItemView: TextView = itemView.findViewById(R.id.cart_count)
        private val cartPriceItemView: TextView = itemView.findViewById(R.id.total_price)



        fun bind(company: String?, beginHour: String, endHour: String, origin: String, destiny: String, connections: Int, totalKMS: Int, basePrice: Float) {
            beginHourItemView.text = beginHour
            endHourItemView.text = endHour
            totalKMSItemView.text = totalKMS.toString() + " kms"
            originItemView.text = origin
            destinyItemView.text = destiny
            connectionsItemView.text = connections.toString() + " conexão"

            buttonItemView.setOnClickListener {
                cart.add(CartItems(beginHour, origin, destiny, basePrice, preferences.getInt("ad_tickets", -1), preferences.getInt("jo_tickets", -1), preferences.getInt("se_tickets", -1)))
                cartCountItemView.text = "Cart: ${cart.size}"
                var totalPrice = 0.0
                for(item in cart){
                    totalPrice += item.preco.toDouble()
                }
                cartPriceItemView.text = totalPrice.toString()
            }


        }

        companion object {
            fun create(parent: ViewGroup): IntercitiesViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return IntercitiesViewHolder(view)
            }
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

}
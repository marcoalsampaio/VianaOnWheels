package com.example.vianaonwheels.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.CartItems
import com.example.vianaonwheels.models.Trips
import java.util.*

class CartAdapter(private val list: ArrayList<CartItems>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val current = list[position]
        holder.bind(current.preco, current.destino, current.origem, current.ida, current.ad_bilhetes, current.jo_bilhetes, current.se_bilhetes )
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idaItemView: TextView = itemView.findViewById(R.id.tv_ida)
        private val precoItemView: TextView = itemView.findViewById(R.id.tv_price)
        private val destinoItemView: TextView = itemView.findViewById(R.id.tv_desPlace)
        private val origemItemView: TextView = itemView.findViewById(R.id.tv_goPlace)
        private val bilhetesItemView: TextView = itemView.findViewById(R.id.tv_tickets)
        fun bind(preco: Float, destino: String, origem: String, ida: String, ad_bilhetes: Int, jo_bilhetes: Int, se_bilhetes: Int) {
            idaItemView.text = "Ida: $ida"
            destinoItemView.text = destino
            origemItemView.text = origem
            var preco_ad : Double = 0.0
            var preco_jo : Double = 0.0
            var preco_se : Double = 0.0

            if(ad_bilhetes > 0){
                preco_ad = (preco + 1.50) * ad_bilhetes
                if(jo_bilhetes > 0){
                    preco_jo = (preco * jo_bilhetes).toDouble()
                    if(se_bilhetes > 0){
                        preco_se = (preco - 1.5) * se_bilhetes
                    }
                }
            }

            precoItemView.text = (preco_ad + preco_jo + preco_se).toString()

            if(ad_bilhetes > 0 || jo_bilhetes > 0 || se_bilhetes > 0){
                bilhetesItemView.text = ""
                if (ad_bilhetes > 0){
                    bilhetesItemView.text = bilhetesItemView.text.toString() + ad_bilhetes.toString() + " "
                }
                if (jo_bilhetes > 0){
                    bilhetesItemView.text = bilhetesItemView.text.toString() + jo_bilhetes.toString() + " "
                }
                if (se_bilhetes > 0){
                    bilhetesItemView.text = bilhetesItemView.text.toString() + se_bilhetes.toString() + " "
                }
            }

        }

        companion object {
            fun create(parent: ViewGroup): CartViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return CartViewHolder(view)
            }
        }
    }

    class CartComparator : DiffUtil.ItemCallback<Trips>() {
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
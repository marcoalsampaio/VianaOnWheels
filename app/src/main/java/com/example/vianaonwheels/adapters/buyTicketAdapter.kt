package com.example.vianaonwheels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.ticketToBuy
import kotlinx.android.synthetic.main.buyticket_line.view.*

class buyTicketAdapter(
    private val ticketsToBuy: ArrayList<ticketToBuy>
): RecyclerView.Adapter<ticketToBuyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ticketToBuyViewHolder {
        return ticketToBuyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.buyticket_line,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ticketToBuyViewHolder, position: Int) {
        val currentTicketToBuy = ticketsToBuy[position]

        holder.partida.text = currentTicketToBuy.partida
        holder.partidaHora.text = currentTicketToBuy.partidaHora
        holder.destino.text = currentTicketToBuy.destino
        holder.destinoHora.text = currentTicketToBuy.destinoHora
        holder.quantidade.text = currentTicketToBuy.quantidade.toString()
        holder.precoUnitario.text = currentTicketToBuy.precoUnitario

    }

    override fun getItemCount(): Int {
        return ticketsToBuy.size
    }

}

class ticketToBuyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val partida = itemView.partida
    val partidaHora = itemView.partidaHora
    val destino = itemView.destino
    val destinoHora = itemView.destinoHora
    val quantidade = itemView.quantidade
    val precoUnitario = itemView.precoUnitario
}
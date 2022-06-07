package com.example.vianaonwheels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.R
import com.example.vianaonwheels.models.ticketToBuy

class buyTicketAdapter(
    private val ticketsToBuy: ArrayList<ticketToBuy>,
    private var totalTV : TextView
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
        holder.precoUnitario.text = currentTicketToBuy.precoUnitario.toString() + "€"
        holder.precoTotalLinha.text = String.format("%.2f", (currentTicketToBuy.precoUnitario * currentTicketToBuy.quantidade)) + "€"

        calculateTotal()

        holder.Mais.setOnClickListener {
            plusOne(position, currentTicketToBuy)
        }

        holder.Menos.setOnClickListener {
            if (currentTicketToBuy.quantidade > 1) {
                minusOne(position, currentTicketToBuy)
            }else{
                deleteItem(position)
            }
        }
    }
    override fun getItemCount(): Int {
        return ticketsToBuy.size
    }

    private fun calculateTotal(){
        var precoTotal = 0.00;
        for (i in ticketsToBuy){
            precoTotal += i.precoTotalLinha
        }
        totalTV.text= String.format("%.2f",precoTotal) +"€"
    }

    private fun plusOne(index: Int, currentTicketToBuy: ticketToBuy){
        ticketsToBuy[index] = ticketToBuy(currentTicketToBuy.partida, currentTicketToBuy.partidaHora, currentTicketToBuy.destino, currentTicketToBuy.destinoHora, currentTicketToBuy.quantidade+1,currentTicketToBuy.precoUnitario, currentTicketToBuy.precoUnitario * (currentTicketToBuy.quantidade+1))
        calculateTotal()
        notifyDataSetChanged()
    }
    private fun minusOne(index: Int, currentTicketToBuy: ticketToBuy){
        ticketsToBuy[index] = ticketToBuy(currentTicketToBuy.partida, currentTicketToBuy.partidaHora, currentTicketToBuy.destino, currentTicketToBuy.destinoHora, currentTicketToBuy.quantidade-1,currentTicketToBuy.precoUnitario,currentTicketToBuy.precoUnitario * (currentTicketToBuy.quantidade-1))
        calculateTotal()
        notifyDataSetChanged()
    }

    private fun deleteItem(index: Int){
        ticketsToBuy.removeAt(index)
        calculateTotal()
        notifyDataSetChanged()
    }

}

class ticketToBuyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val partida = itemView.findViewById<TextView>(R.id.partida)
    val partidaHora = itemView.findViewById<TextView>(R.id.partidaHora)
    val destino = itemView.findViewById<TextView>(R.id.destino)
    val destinoHora = itemView.findViewById<TextView>(R.id.destinoHora)
    val quantidade = itemView.findViewById<TextView>(R.id.quantidade)
    val precoUnitario = itemView.findViewById<TextView>(R.id.precoUnitario)
    val Mais = itemView.findViewById<TextView>(R.id.Mais)
    val Menos = itemView.findViewById<TextView>(R.id.Menos)
    val precoTotalLinha = itemView.findViewById<TextView>(R.id.precoTotalLinha)
}
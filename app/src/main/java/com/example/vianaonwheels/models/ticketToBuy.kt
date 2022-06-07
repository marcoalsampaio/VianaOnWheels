package com.example.vianaonwheels.models

import kotlinx.android.synthetic.main.buyticket_line.view.*

class ticketToBuy(
    val partida: String,
    val partidaHora: String,
    val destino: String,
    val destinoHora: String,
    val quantidade: Int,
    val precoUnitario: Double,
    val precoTotalLinha: Double) {
}

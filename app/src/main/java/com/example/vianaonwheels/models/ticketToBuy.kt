package com.example.vianaonwheels.models

class ticketToBuy(
    val data: String,
    val company: String,
    val partida: String,
    val partidaHora: String,
    val destino: String,
    val destinoHora: String,
    var quantidade: Int,
    val precoUnitario: Double,
    var precoTotalLinha: Double) {
}

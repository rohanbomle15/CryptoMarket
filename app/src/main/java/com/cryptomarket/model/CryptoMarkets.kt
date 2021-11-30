package com.cryptomarket.model

data class Markets (var markets: List<Market>)

data class Market(
    var exchange_id: String,
    var symbol: String,
    var base_asset: String,
    var quote_asset: String,
    var price_unconverted: Double,
    var price: Double,
    var change_24h: Double,
    var spread: Double,
    var volume_24h: Double,
    var status: String
)
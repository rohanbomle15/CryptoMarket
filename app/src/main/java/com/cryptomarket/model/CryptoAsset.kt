package com.cryptomarket.model

import com.google.gson.annotations.SerializedName

data class CryptoAsset(
    @SerializedName("data")
    var cryptoData: List<CryptoAssetDetails>
)

data class CryptoAssetDetails(
    @SerializedName("id")
    var id: String,
    @SerializedName("rank")
    var rank: Int,
    @SerializedName("symbol")
    var symbol: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("supply")
    var supply: Double,
    @SerializedName("maxSupply")
    var maxSupply: Double,
    @SerializedName("marketCapUsd")
    var marketCapUsd: Double,
    @SerializedName("volumeUsd24Hr")
    var volumeUsd24Hr: Double,
    @SerializedName("priceUsd")
    var priceUsd: Double,
    @SerializedName("changePercent24Hr")
    var changePercent24Hr: Double,
    @SerializedName("vwap24Hr")
    var vwap24Hr: Double
)
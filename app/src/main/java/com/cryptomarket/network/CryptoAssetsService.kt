package com.cryptomarket.network

import com.cryptomarket.model.Markets
import retrofit2.http.GET

interface CryptoAssetsService {

    @GET("markets")
    suspend fun getCryptoAssets(): Markets
}